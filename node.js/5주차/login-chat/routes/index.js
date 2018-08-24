const express = require('express');
const router = express.Router();
const path = require('path');

router.get('/', function(req, res, next) {
  res.sendFile(path.join(
    __dirname, '..', 'views', 'index.html'));
});

router.get('/logout', function(req, res, next) {
  console.log('session is : ' + req.session.name);

  if(req.session.name){
    req.session.destroy(function(err){
      if(err){
        console.log(err);
        return res.sendFile(path.join(
          __dirname, '..', 'views', 'fail.html'));
      }else{
        res.redirect('/');
      }
    });
  }

});

module.exports = router;
