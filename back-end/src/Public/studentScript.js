const template = document.querySelector("#student-template");
const container = document.querySelector(".students-container");
const apiURL = "http://localhost:8000";

(() => {
    getStudents();
    addNewStudent();
})();


function getStudents() {
    fetch(`${apiURL}/student`)

        .then(function (response) {
            return response.json();
        })
        .then(function (students) {
            innerStudents(students);
        })
}


function innerStudents(students){
    students.forEach(student => {
        const clone = document.importNode(template.content, true);
        clone.querySelector('.name').textContent = student.name;
        clone.querySelector('.surname').textContent = student.surname;
        clone.querySelector('.email').textContent = student.email;
        clone.querySelector('.phone').textContent = student.phoneNumber;

         const profile = clone.querySelector('.profile');
         profile.setAttribute("data-id", student.userDetailsID);
         profile.addEventListener("click", function () {

                 document.location.href='studentProfile.html';
         })

        const edit = clone.querySelector('.popup');
        edit.addEventListener("click", popUp);

        const editName = clone.querySelector('#edit-name');
        editName.setAttribute("data-id", student.userDetailsID);
        editName.addEventListener("click", editNameFunction);

        const editSurname = clone.querySelector('#edit-surname');
        editSurname.setAttribute("data-id", student.userDetailsID);
        editSurname.addEventListener("click", editSurnameFunction);

        const editMail = clone.querySelector('#edit-mail');
        editMail.setAttribute("data-id", student.userDetailsID);
        editMail.addEventListener("click", editMailFunction);

        const editPhone = clone.querySelector('#edit-phone');
        editPhone.setAttribute("data-id", student.userDetailsID);
        editPhone.addEventListener("click", editPhoneFunction);


        const remButt = clone.querySelector('.removeButton');
        remButt.setAttribute("data-id", student.userDetailsID);
        remButt.addEventListener("click", removeStudent);
        container.appendChild(clone);
    })
}


function removeStudent(){
    const id = this.getAttribute("data-id");
    fetch(`${apiURL}/student/${id}/remove`, {
        // mode: 'cors',
        method: "POST" }
    )
        .then(function (response) {
            return response.json();

        })
        .then(function (students) {
            clearStudents();
            innerStudents(students);
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
    fetch(`${apiURL}/student/${id}/edit-name/${newName}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (students) {
            clearStudents();
            innerStudents(students)
        });
}

function editSurnameFunction() {
    const id = this.getAttribute("data-id");
    const newSurname = prompt("Enter new Surname", 'Surname');
    fetch(`${apiURL}/student/${id}/edit-surname/${newSurname}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (students) {
            clearStudents();
            innerStudents(students);
        });
}

function editMailFunction() {
    const id = this.getAttribute("data-id");
    const newMail = prompt("Enter new e-mail", 'example@this.is');
    fetch(`${apiURL}/student/${id}/edit-mail/${newMail}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (students) {
            clearStudents();
            innerStudents(students);

        });
}

function editPhoneFunction() {
    const id = this.getAttribute("data-id");
    const newPhone = prompt("Enter new phone number", '444 222 000');
    console.log(newPhone);
    fetch(`${apiURL}/student/${id}/edit-phone/${newPhone}`, {
        method: "POST" }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (students) {
            clearStudents();
            innerStudents(students);
        });
}

function addNewStudent() {
    document.getElementById("add").addEventListener("click", function () {
        fetch(`${apiURL}/student/new/add`, {
            // mode: 'cors',
            method: "POST" }
        )
            .then(function (response) {
                return response.json();
            })
            .then(function (students) {
                clearStudents();
                innerStudents(students);
                // location.reload();
            });

    });
}

function clearStudents() {
    const studentsToClear = document.querySelector(".students-container");
    while (studentsToClear.lastElementChild) {
        studentsToClear.removeChild(studentsToClear.lastElementChild);
    }
}
