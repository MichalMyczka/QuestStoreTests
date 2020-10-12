const loginForm = document.querySelector("#login-form");

loginForm.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = `email=${this.email.value}&password=${this.password.value}`;
    console.log(data);
    login(data);
});

function login(data) {
    fetch("http://localhost:8000/login",
        {
            credentials: 'same-origin',
            method: "POST",
            body: data
        })
        .then((response) => {
            return response.json()
        })
        .then((responseJson) => {
            document.cookie = "user=" + responseJson.id + " " + responseJson.email + " " +responseJson.role;
            return responseJson;
        })
        .then ((responseJson) => {
            if (responseJson.role === "student") {
                window.location.href = "studentProfile.html";
            }
            if (responseJson.role === "mentor") {
                window.location.href = "mentorView.html";
            }
            if (responseJson.role === "creep") {
                window.location.href = "creepView.html";
            }
        })
        .catch(error => console.error(error));
}