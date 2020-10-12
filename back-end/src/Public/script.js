/*
function myFunction() {
    document.getElementById("demo").innerHTML = "confirm pass";
}
*/

const form = document.querySelector('form');

(() => {
    /*    initShowSignInForm();*/
    /*    form.addEventListener('submit', function(e) {
            e.preventDefault();*/
    /*       showSignInForm();*/
    /*    });*/
    openSlideMenu();
    closeSlideMenu();
})();


function showSignInForm() {
    const confirmPassword = document.querySelector("input[name='confirm_password']");
    if (confirmPassword.style.display === 'block') {
        //to do send register form to backend
    }
    else {
        confirmPassword.style.display = 'block';
    }
}
/*
function initShowSignInForm() {
    const signIn = document.querySelector('form > .submit');
/!*    signIn.addEventListener("click", showSignInForm)*!/
}*/

function openSlideMenu() {
    document.getElementById('menu').style.width = '250px';
}

function closeSlideMenu() {
    document.getElementById('menu').style.width = '0';
}
