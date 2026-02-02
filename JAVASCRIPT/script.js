// Programmer Identifier: Wiley Javier - 123456789

// CSV content as multi-line string
const csvData = `StudentID,first_name,last_name
073900438,Osbourne,Wakenshaw
114924014,Albie,Gierardi
111901632,Eleen,Pentony
084000084,Arie,Okenden
272471551,Alica,Muckley`;

let students = csvData.split('\n').slice(1).map(line => {
    const [id, first, last] = line.split(',');
    return { id, first, last };
});

const tbody = document.querySelector('#studentTable tbody');

function render() {
    tbody.innerHTML = '';
    students.forEach((s, index) => {
        tbody.innerHTML += `<tr>
            <td>${s.id}</td>
            <td>${s.first}</td>
            <td>${s.last}</td>
            <td><button onclick="deleteStudent(${index})">Delete</button></td>
        </tr>`;
    });
}

function deleteStudent(index) {
    students.splice(index, 1);
    render();
}

document.getElementById('studentForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const id = document.getElementById('studentID').value;
    const first = document.getElementById('firstName').value;
    const last = document.getElementById('lastName').value;
    students.push({id, first, last});
    render();
    this.reset();
});

render();
