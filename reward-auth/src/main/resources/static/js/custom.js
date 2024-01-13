$(document).ready(function() {
  $("#show_hide_password span i:first-child").on('click', function(event) {
      event.preventDefault();
      if($('#show_hide_password input').attr("type") == "text"){
          $('#show_hide_password input').attr('type', 'password');
          $('#show_hide_password i:first-child').addClass( "fa-eye-slash" );
          $('#show_hide_password i:first-child').removeClass( "fa-eye" );
      }else if($('#show_hide_password input').attr("type") == "password"){
          $('#show_hide_password input').attr('type', 'text');
          $('#show_hide_password i:first-child').removeClass( "fa-eye-slash" );
          $('#show_hide_password i:first-child').addClass( "fa-eye" );
      }
  });
});

$(document).ready(function() {
    if ($("#username").val().length !== 0 && $.trim($('#username').val()) !== ''
        && $("#password").val().length !== 0 && $.trim($('#password').val()) !== '') {
        $('#login-btn').removeAttr('disabled');
    }

    $("#username").keyup(function(){
        if($('#username').val() != '' && $('#password').val() != '') {
           $('#login-btn').removeAttr("disabled");
        } else {
           $('#login-btn').attr("disabled", true);
        }
     });

     $("#username").blur(function(){
         if($('#username').val() != '' && $('#password').val() != '') {
            $('#login-btn').removeAttr("disabled");
         } else {
            $('#login-btn').attr("disabled", true);
         }
      });

    $("#password").keyup(function(){
        if($('#username').val() != '' && $('#password').val() != '') {
           $('#login-btn').removeAttr("disabled");
        } else {
          $('#login-btn').attr("disabled", true);
        }
     });

     $("#password").blur(function(){
         if($('#username').val() != '' && $('#password').val() != '') {
            $('#login-btn').removeAttr("disabled");
            setTimeout(function(){$( "#login-btn" ).focus();});
            } else {
           $('#login-btn').attr("disabled", true);
         }
      });

    $(document).on('keypress',function(e) {
        if(e.which == 13 &&  $('#username').val() != '' && $('#password').val() != '') {
            $('#login-btn').trigger('click');
        }
    });

     $("#login-btn").click(function(){
        if ($('#saveId').is(":checked")) {
          var userId = $("#username").val();
          localStorage.removeItem('ib_userid');
          localStorage.setItem('ib_userid', userId);
        }
        else {
          localStorage.removeItem('ib_userid');
        }
      });
 });

 $( document ).ready(function() {
    var userId = localStorage.getItem('ib_userid');
    if(userId !== undefined && userId !== null && userId !== ''){
        $("#username").val(userId);
        $("#saveId").prop('checked', true);
    }
 });

 var arrLang = {
       'en': {
                'login': 'Login',
                'branch': 'Branch',
                'atm': 'ATM',
                'help': 'Help',
                'open-account': 'Open Bank Account',
                'register': 'Register',
                'forget': 'Forgot password?',
           'request': 'Request for a new AW User',
                'login-button': 'Login',
                'save-id': 'Save ID',
                'user-id': 'User ID',
                'password': 'Password',
                'enter-user-id': 'Enter your User ID',
                'enter-user-password': 'Enter your Password',
                'welcome': 'Welcome',
                'to your UMS Platform': 'to your UMS Platform',
                'invalid-uid-pass': 'Invalid User ID or Password.'
              },
              'bn': {
                'login': 'লগইন',
                'branch': 'শাখা',
                'atm': 'এটিএম',
                'help': 'সাহায্য',
                'open-account': 'ব্যাংক অ্যাকাউন্ট খুলুন',
                'register': 'নিবন্ধন',
                'forget': 'পাসওয়ার্ড ভুলে গেছেন?',
                  'request': 'একজন নতুন AW ইউজারের জন্য অনুরোধ করুন',
                'login-button': 'লগইন',
                'save-id': 'আইডি সংরক্ষণ',
                'user-id': 'ব্যবহারকারীর আইডি',
                'password': 'পাসওয়ার্ড',
                'enter-user-id': 'আইডি লিখুন',
                'enter-user-password': 'পাসওয়ার্ড লিখুন',
                'welcome': 'স্বাগতম',
                'to your UMS Platform': 'আপনার ইউএমএস প্ল্যাটফর্ম',
                'invalid-uid-pass': 'ব্যবহারকারীর আইডি বা পাসওয়ার্ড ভুল'
              }
     };

 $(function() {
   $('.translate').click(function() {
     var lang = $(this).attr('id');
     if(lang == 'bn'){
          $('#bn').addClass( "active" );
          $('#en').removeClass( "active" );
     } else {
        $('#en').addClass( "active" );
        $('#bn').removeClass( "active" );
     }
     $('.lang').each(function(index, item) {
       $(this).text(arrLang[lang][$(this).attr('key')]);
     });
     $(this).text(arrLang[lang][$(this).attr('key')]);
     $('#username').attr('placeholder', arrLang[lang]['enter-user-id'])
     $('#password').attr('placeholder', arrLang[lang]['enter-user-password'])
   });
 });

 $('#username').keyboard({
   reposition: true,
   usePreview: false,
   openOn : '',
   stayOpen : false,
   autoAccept: true,
   autoAcceptOnEsc: true,
   tabNavigation: true,
   enterNavigation: true,
   appendLocally: true,
   layout: 'custom',
   customLayout: {
       'normal': [
           '` 1 2 3 4 5 6 7 8 9 0 - = {del} {b}',
           '{tab} q w e r t y u i o p [ ] \\',
           'a s d f g h j k l ; \' {enter}',
           '{shift} z x c v b n m , . / {shift}',
           '{cancel} {space} {left} {right}'
       ],
       'shift': [
           '~ ! @ # $ % ^ & * ( ) _ + {del} {b}',
           '{tab} Q W E R T Y U I O P { } |',
           'A S D F G H J K L : " {enter}',
           '{shift} Z X C V B N M < > ? {shift}',
           '{cancel} {space} {left} {right}'
       ]
   },
   css: {
       input: 'form-control input-sm',
       container: 'center-block dropdown-menu',
       buttonDefault: 'btn btn-secondary',
       buttonAction: 'highlights-action-keys',
       buttonDisabled: 'disabled'
   },
   beforeClose: function(e, keyboard, el, accepted) {
       $('#userIdKeyboard').css("pointer-events", "auto").css("touch-action", "auto");
   }
});
$('#userIdKeyboard').click(function() {
   $('#username').getkeyboard().reveal();
   $('#userIdKeyboard').css("pointer-events", "none").css("touch-action", "none");
});
$('#password').keyboard({
   reposition: true,
   usePreview: false,
   openOn : '',
   stayOpen : false,
   autoAccept: true,
   autoAcceptOnEsc: true,
   tabNavigation: true,
   enterNavigation: true,
   appendLocally: true,
   layout: 'custom',
   customLayout: {
       'normal': [
           '` 1 2 3 4 5 6 7 8 9 0 - = {del} {b}',
           '{tab} q w e r t y u i o p [ ] \\',
           'a s d f g h j k l ; \' {enter}',
           '{shift} z x c v b n m , . / {shift}',
           '{cancel} {space} {left} {right}'
       ],
       'shift': [
           '~ ! @ # $ % ^ & * ( ) _ + {del} {b}',
           '{tab} Q W E R T Y U I O P { } |',
           'A S D F G H J K L : " {enter}',
           '{shift} Z X C V B N M < > ? {shift}',
           '{cancel} {space} {left} {right}'
       ]
   },
   css: {
       input: 'form-control input-sm',
       container: 'center-block dropdown-menu',
       buttonDefault: 'btn btn-secondary',
       buttonAction: 'highlights-action-keys',
       buttonDisabled: 'disabled'
   },
   beforeClose: function(e, keyboard, el, accepted) {
       $('#passwordKeyboard').css("pointer-events", "auto").css("touch-action", "auto");
   }
});
$('#passwordKeyboard').click(function() {
  $('#password').getkeyboard().reveal();
   $('#passwordKeyboard').css("pointer-events", "none").css("touch-action", "none");
});