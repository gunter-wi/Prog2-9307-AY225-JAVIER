const USERS = [
    { username: "wi", password: "123" },
    { username: "nash", password: "cansino" },
    { username: "don", password: "pogi" },
    { username: "maricar", password: "maricakes" },
    { username: "naz", password: "nazcute" }
];

function login() {
    const userInput = document.getElementById("username").value;
    const passInput = document.getElementById("password").value;
    const result = document.getElementById("result");

    const now = new Date();
    const timestamp = now.toLocaleString();

    const matchedUser = USERS.find(user => user.username === userInput && user.password === passInput);

    if (matchedUser) {
        result.textContent = `Welcome, ${matchedUser.username}! Login Successful at ${timestamp}`;
        result.style.color = "green";
        saveAttendance(userInput, timestamp, "SUCCESS");
    } else {
        playWrongBeep();
        result.textContent = `Incorrect Username or Password! ${timestamp}`;
        result.style.color = "red";
        saveAttendance(userInput, timestamp, "FAILED");
    }
}

function playWrongBeep() {
    const audio = new Audio('beep.wav');
    audio.play();
}

function saveAttendance(username, time, status) {
    const today = new Date();
    const dateStr = today.toISOString().split('T')[0]; // YYYY-MM-DD
    const fileName = `attendance_${dateStr}.txt`;

    const data =
        "Username: " + username + "\n" +
        "Time: " + time + "\n" +
        "Status: " + status + "\n" +
        "-----------------------------\n";

    const blob = new Blob([data], { type: "text/plain" });
    const link = document.createElement("a");

    link.href = URL.createObjectURL(blob);
    link.download = fileName;
    link.click();
}
