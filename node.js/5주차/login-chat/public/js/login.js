$(function() {

  $('#login-form-link').click(function(e) {
    $("#login-form").delay(100).fadeIn(100);
    $("#register-form").fadeOut(100);
    $('#register-form-link').removeClass('active');
    $(this).addClass('active');
    e.preventDefault();
  });
  $('#register-form-link').click(function(e) {
    $("#register-form").delay(100).fadeIn(100);
    $("#login-form").fadeOut(100);
    $('#login-form-link').removeClass('active');
    $(this).addClass('active');
    e.preventDefault();
  });
  $('login-submit').click(function(e) {
    console.log('test1');
  })
  $('register-submit').click(function(e) {
    console.log('test2');
  })

  //login-submit <- 로그인 버튼 아이디
  //register-submit <- 회원가입 버튼 아이디
});
