const express = require('express');
const router = express.Router();
const path = require('path');
const MongoClient = require('mongodb').MongoClient;
const env = require('../config/env');

router.post('/signIn', function(req, res, next) {
  let name = req.body.username;
  let password = req.body.password;
  let databaseUrl = 'mongodb://' + env.url + ':' + env.port + '/' + env.database;

  MongoClient.connect(databaseUrl, { useNewUrlParser: true}, function(err, db){
    if(err) throw err;

    let database = db.db('userInfo');
    let users = database.collection('users');

    users.find({'name' : name, 'password' : password}).toArray(function(err, docs){
      if(err) throw err;

      if(docs.length > 0){
        db.close();
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'chat.html'));
      }else{
        db.close();
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'fail.html'));
      }
    });

  });

});

router.post('/signUp', function(req, res, next) {
  let name = req.body.user;
  let password = req.body.pass;
  let confirm = req.body.confirmPassword;
  let databaseUrl = 'mongodb://' + env.url + ':' + env.port + '/' + env.database;

  console.log(password);
  console.log(confirm);

  if(password !== confirm){
    return res.sendFile(path.join(
      __dirname, '..', 'views', 'fail.html'));
  }

  MongoClient.connect(databaseUrl, { useNewUrlParser: true}, function(err, db){
    if(err) throw err;

    let database = db.db('userInfo');
    let users = database.collection('users');

    users.insertOne({'name' : name, 'password' : password}, function(err, client){
      if(err) throw err;

      if(client.result.ok === 1){
        db.close();
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'success.html'));

      }else{
        db.close();
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'fail.html'));
      }
    });

  });
});

module.exports = router;
