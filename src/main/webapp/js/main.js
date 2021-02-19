$(document).ready(function(){

    // register btn click
    $('#register-btn').click(function(){
        $('#register-modal').modal('show');
    });

    // login click
    function loginClick (e){

        // Prevent the default submit action
        e.preventDefault();
    
        var userValid = simpleValidation($('#username')),
            passValid = simpleValidation($('#password'));
    
        if(userValid && passValid){
            parent.window.location.href = "home.html";
        }
    }

    function simpleValidation($input){
        var valid = true,
            value = $input.val();
    
            if(value.length == 0){
                valid = false;
                $input.closest('.form-group').find('.help-block').show();
            }else{
                $input.closest('.form-group').find('.help-block').hide();
            }
    
        return valid;
    }
});



