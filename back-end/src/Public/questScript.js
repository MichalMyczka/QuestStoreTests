const template = document.querySelector("#quest-template");
const container = document.querySelector(".cards-container");
const apiURL = "http://localhost:8000"

function getQuests() {
    fetch(`${apiURL}/quest`)

        .then(function (response) {
            return response.json();
        })
        .then(function (quests) {
            innerMentors(quests);
        })
}


function innerMentors(quests){
    quests.forEach(quest => {
        const clone = document.importNode(template.content, true);
        clone.querySelector('.price-tag').textContent = quest.value + "$";
        clone.querySelector('.quest-name').textContent = quest.name;
        clone.querySelector('.description').textContent = quest.description;
        container.appendChild(clone);
    })

}

getQuests();

