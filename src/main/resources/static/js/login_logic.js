function checkBeforeLogin(){
    if(!document.loginForm.username.value ||  !document.loginForm.password.value){
        document.getElementById("messageNotBlank").classList.remove("display-none");
        return;
    }
    SubmitForm();
}

//フォームを送信する。
function SubmitForm(){
    document.loginForm.submit();
}