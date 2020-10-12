const apiURL = "http://localhost:8000";
let cookie = "";




function getCookie() {
    cookie = document.cookie;
    console.log(cookie);
}




function generateProfile(mentor){
    console.log(document.querySelector('.name').textContent = mentor.name);
    document.querySelector('.name').textContent = mentor.name;
    document.querySelector('.email').textContent = mentor.email;
    document.querySelector('.surname').textContent = mentor.surname;
    document.querySelector('.phone').textContent = mentor.phoneNumber;

}

function getMentor() {
        fetch(`${apiURL}/mentorView/profile/${cookie}`, {
            // mode: 'cors',
            method: "POST" }
        )
            .then(function (response) {
                return response.json();
            })
            .then(function (mentor) {
                generateProfile(mentor);
            });
}


getCookie();
getMentor()

