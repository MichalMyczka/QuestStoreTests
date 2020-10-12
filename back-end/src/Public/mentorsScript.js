const template = document.querySelector("#mentor-template");
const container = document.querySelector(".mentors-container");
const apiURL = "http://localhost:8000";

(() => {
    getMentors();
    addNewMentor();
})();


function getMentors() {
    fetch(`${apiURL}/mentor`)

        .then(function (response) {
            return response.json();
        })
        .then(function (mentors) {
            innerMentors(mentors);
        })
}


function innerMentors(mentors){
    mentors.forEach(mentor => {
        const clone = document.importNode(template.content, true);
        clone.querySelector('.name').textContent = mentor.name;
        clone.querySelector('.surname').textContent = mentor.surname;
        clone.querySelector('.email').textContent = mentor.email;
        clone.querySelector('.phone').textContent = mentor.phoneNumber;

        const edit = clone.querySelector('.popup');
        edit.addEventListener("click", popUp);

        const editName = clone.querySelector('#edit-name');
        editName.setAttribute("data-id", mentor.userDetailsID);
        editName.addEventListener("click", editNameFunction);

        const editSurname = clone.querySelector('#edit-surname');
        editSurname.setAttribute("data-id", mentor.userDetailsID);
        editSurname.addEventListener("click", editSurnameFunction);

        const editMail = clone.querySelector('#edit-mail');
        editMail.setAttribute("data-id", mentor.userDetailsID);
        editMail.addEventListener("click", editMailFunction);

        const editPhone = clone.querySelector('#edit-phone');
        editPhone.setAttribute("data-id", mentor.userDetailsID);
        editPhone.addEventListener("click", editPhoneFunction);


        const remButt = clone.querySelector('.removeButton');
        remButt.setAttribute("data-id", mentor.userDetailsID);
        remButt.addEventListener("click", removeMentor);
        container.appendChild(clone);
    })
}


function removeMentor(){
    const id = this.getAttribute("data-id");
    fetch(`${apiURL}/mentor/${id}/remove`, {
        // mode: 'cors',
        method: "POST" }
    )
        .then(function (response) {
            return response.json();

        })
        .then(function (mentors) {
            clearMentors();
            innerMentors(mentors);
        });
}


function popUp() {
    // const id = this.getAttribute("data-id");
    var popup = this.lastElementChild;
    popup.classList.toggle("show");
}


function editNameFunction() {
    const id = this.getAttribute("data-id");
    const newName = prompt("Enter new name", 'Name');
    fetch(`${apiURL}/mentor/${id}/edit-name/${newName}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (mentors) {
            clearMentors();
            innerMentors(mentors)
        });
}

function editSurnameFunction() {
    const id = this.getAttribute("data-id");
    const newSurname = prompt("Enter new Surname", 'Surname');
    fetch(`${apiURL}/mentor/${id}/edit-surname/${newSurname}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (mentors) {
            clearMentors();
            innerMentors(mentors);
        });
}

function editMailFunction() {
    const id = this.getAttribute("data-id");
    const newMail = prompt("Enter new e-mail", 'example@this.is');
    fetch(`${apiURL}/mentor/${id}/edit-mail/${newMail}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (mentors) {
            clearMentors();
            innerMentors(mentors);

        });
}

function editPhoneFunction() {
    const id = this.getAttribute("data-id");
    const newPhone = prompt("Enter new phone number", '444 222 000');
    console.log(newPhone);
    fetch(`${apiURL}/mentor/${id}/edit-phone/${newPhone}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (mentors) {
            clearMentors();
            innerMentors(mentors);
        });
}

function addNewMentor() {
    document.getElementById("add").addEventListener("click", function () {
        fetch(`${apiURL}/mentor/new/add`, {
            // mode: 'cors',
            method: "POST" }
        )
            .then(function (response) {
                return response.json();
            })
            .then(function (mentors) {
                clearMentors();
                innerMentors(mentors);
                // location.reload();
            });

    });
}

function clearMentors() {
    const mentorsToClear = document.querySelector(".mentors-container");
    while (mentorsToClear.lastElementChild) {
        mentorsToClear.removeChild(mentorsToClear.lastElementChild);
    }
}
