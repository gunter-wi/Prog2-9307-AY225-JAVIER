const fs = require("fs");

const filePath = "Sample_Data-Prog-2-csv.csv";
let recordCount = 0;

try {
    const data = fs.readFileSync(filePath, "utf8");
    const lines = data.split("\n");

    for (let line of lines) {
        line = line.trim();

        if (line.startsWith('"')) { // valid candidate row
            recordCount++;
        }
    }

    console.log("Total number of records:", recordCount);

} catch (err) {
    console.log("Error reading file:", err.message);
}