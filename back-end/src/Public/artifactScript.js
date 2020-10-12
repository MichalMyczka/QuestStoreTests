const template = document.querySelector("#artifact-template");
const container = document.querySelector(".cards-container");
const apiURL = "http://localhost:8000"

function getArtifacts() {
    fetch(`${apiURL}/shop`)

        .then(function (response) {
            return response.json();
        })
        .then(function (artifacts) {
            innerArtifacts(artifacts);
        })
}



function innerArtifacts(artifacts){
    artifacts.forEach(artifact => {
        const clone = document.importNode(template.content, true);
        clone.querySelector('.artifact-name').textContent = artifact.name;
        clone.querySelector('.description').textContent = artifact.description;
        clone.querySelector('.price-tag').textContent = artifact.price + "$";
        container.appendChild(clone);
    })

}

getArtifacts();

