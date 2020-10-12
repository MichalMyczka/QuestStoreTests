const cookie = document.cookie;
const balance = document.querySelector("#balance");
const apiURL = "http://localhost:8000";
const template = document.querySelector("#artifact-template");
const container = document.querySelector(".cards-container");

(() => {
    getBalance();
    getUserArtifacts();
})();


    function getBalance(){
        const id = cookie.replace(/ .*/,'').substring(5);
        fetch(`${apiURL}/wallet/${id}/balance`, {
            // mode: 'cors',
            method: "GET" }
        )
            .then(function (response) {
                return response.json();

            })
            .then(function (amount){
                setBalance(amount);
            });
    }

    function setBalance(amount) {
        balance.textContent = amount;
    }



function getUserArtifacts() {
        const id = cookie.replace(/ .*/,'').substring(5);
        fetch(`${apiURL}/wallet/${id}/cards`, {
            // mode: 'cors',
            method: "GET" }
        )
            .then(function (response) {
                return response.json();

            })
            .then(function (artifacts){
                setArtifacts(artifacts);
            });
    }

function setArtifacts(artifacts) {
    artifacts.forEach(artifact => {
        const clone = document.importNode(template.content, true);
        clone.querySelector('.artifact-name').textContent = artifact.name;
        clone.querySelector('.description').textContent = artifact.description;
        container.appendChild(clone);
    })
}