function computeGrades() {
    const attendance = parseFloat(document.getElementById("attendance").value);
    const lab1 = parseFloat(document.getElementById("lab1").value);
    const lab2 = parseFloat(document.getElementById("lab2").value);
    const lab3 = parseFloat(document.getElementById("lab3").value);

    
    if ([attendance, lab1, lab2, lab3].some(x => isNaN(x) || x < 0 || x > 100)) {
        document.getElementById("output").textContent = "⚠ Please enter valid grades between 0 and 100 for all fields.";
        return;
    }

    const labAverage = (lab1 + lab2 + lab3) / 3;
    const classStanding = (attendance * 0.40) + (labAverage * 0.60);

    let requiredPass = (75 - (classStanding * 0.30)) / 0.70;
    let requiredExcellent = (100 - (classStanding * 0.30)) / 0.70;

    if (requiredPass > 100) requiredPass = 100;
    if (requiredExcellent > 100) requiredExcellent = 100;

    let output = "";

    output += "Attendance Grade: " + attendance + "\n";
    output += "Explanation: This score represents your attendance performance.\n\n";

    output += "Lab Work 1: " + lab1 + "\n";
    output += "Lab Work 2: " + lab2 + "\n";
    output += "Lab Work 3: " + lab3 + "\n\n";

    output += "Lab Work Average: " + labAverage.toFixed(2) + "\n";
    output += "Explanation: This is the average of your three lab work grades.\n\n";

    output += "Class Standing: " + classStanding.toFixed(2) + "\n";
    output += "Explanation: Class Standing is computed as 40% Attendance and 60% Lab Work Average.\n\n";

    output += "Required Prelim Exam Grade:\n";
    output += "To Pass (75): " + requiredPass.toFixed(2) + "\n";
    output += "To Excel (100): " + requiredExcellent.toFixed(2) + "\n\n";

    
    output += "Final Remark: ";
    if (requiredPass <= 75) {
        output += "You are in good standing and can pass the Prelim period with a reasonable exam score.";
    } else if (requiredPass <= 100) {
        output += "You can still pass the Prelim period, but you need to score high on the exam.";
    } else {
        output += "Passing the Prelim period is not achievable with your current Class Standing.";
    }

    document.getElementById("output").textContent = output;
}
