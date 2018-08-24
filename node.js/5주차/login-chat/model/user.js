const mongoose = require('mongoose');
const crypto = require('crypto');

let Schema = mongoose.Schema;

let userSchema = new Schema({
  name: {type: String, required: true, unique: true},
  password: {type: String, required: true},
  salt: {type : String, required: true}
});

userSchema
  .virtual('info')
  .set(function(info){
    let splitted = info.split(' ');
    this.name = splitted[0];
    this.pw = splitted[1];
    this.salt = this.makeSalt();
    this.password = this.encryptPassword(this.pw);
  })
  // .get(function(){
  //   return this.name + ' ' + this.password;
  // });

userSchema.static('findByName', function(name, callback){
  return this.find({name : name}, callback);
});

userSchema.method('encryptPassword', function(plainText, inSalt){
  if(inSalt){
    return crypto.createHmac('sha1', inSalt).update(plainText).digest('hex');
  }else{
    return crypto.createHmac('sha1', this.salt).update(plainText).digest('hex');
  }
});

userSchema.method('makeSalt', function(){
  return Math.round((new Date().valueOf() * Math.random())) + '';
});

userSchema.method('authenticate', function(plainText, inSalt, password){
  if(inSalt){
    return this.encryptPassword(plainText, inSalt) === password;
  }else{
    return this.encryptPassword(plainText) === password;
  }
});

module.exports = mongoose.model('users', userSchema);
