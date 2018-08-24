const express = require('express');
const router = express.Router();
const path = require('path');
const MongoClient = require('mongodb').MongoClient;
const mongoose = require('mongoose');
const env = require('../config/env');
const User = require('../model/user');
//User는 몽고디비 스키마를 정의한 것

router.post('/signIn', function(req, res, next) {
  let name = req.body.username;
  let password = req.body.password;
  let databaseUrl = 'mongodb://' + env.url + ':' + env.port + '/' + env.database;

  mongoose.connect(databaseUrl, { useNewUrlParser: true}, function(err, db){
    if(err){
      console.log(err);
      return res.sendFile(path.join(
        __dirname, '..', 'views', 'fail.html'));
    }

    User.findByName(name, function(err, results){
      if(err){
        console.log(err);
        db.close();
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'fail.html'));
      }

      if(results.length > 0){
        let user = new User({name : name});
        let authenticated = user.authenticate(password, results[0]._doc.salt,
        results[0]._doc.password);

        if(authenticated){
          db.close();
          req.session.name = name;
          return res.sendFile(path.join(
            __dirname, '..', 'views', 'chat.html'));
            //아이디와 비밀번호가 모두 일치시 로그인 성공
        }else{
          db.close();
          return res.sendFile(path.join(
            __dirname, '..', 'views', 'fail.html'));
            //로그인 실패
        }
      }

    });

  });

});

router.post('/signUp', function(req, res, next) {
  let name = req.body.user;
  let password = req.body.pass;
  let confirm = req.body.confirmPassword;
  let databaseUrl = 'mongodb://' + env.url + ':' + env.port + '/' + env.database;

  if(password !== confirm){
    return res.sendFile(path.join(
      __dirname, '..', 'views', 'fail.html'));
      //비밀번호와 비밀번호 확인이 다를시 가입 실패
  }

  mongoose.connect(databaseUrl, { useNewUrlParser: true}, function(err, db){
    if(err){
      console.log(err);
      return res.sendFile(path.join(
        __dirname, '..', 'views', 'fail.html'));
    }

    let user = new User({'info' : name + ' ' + password});

    user.save(function(err){
      if(err){
        db.close();
        if(err.code === 11000){
          console.log('dulicate key error');
          //중복에러에 대한 처리를 여기서
        }
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'fail.html'));
      }else{
        db.close();
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'success.html'));
          //가입성공
      }

    });

  });
});

module.exports = router;
