const fs = require('fs');
const readline = require('readline');

// CSV file path
const filePath = "Sample_Data-Prog-2-csv.csv";

// Create readline interface for user input
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// Function to search CSV
function searchCSV(keyword) {
    return new Promise((resolve, reject) => {
        if (!fs.existsSync(filePath)) {
            console.log("CSV file not found.");
            resolve();
            return;
        }

        let foundCount = 0;
        const fileStream = fs.createReadStream(filePath);
        const reader = readline.createInterface({ input: fileStream });

        reader.on('line', (line) => {
            line = line.trim();
            if (line === "" || line.startsWith("Candidate")) return;

            if (line.toLowerCase().includes(keyword.toLowerCase())) {
                console.log(line);
                foundCount++;
            }
        });

        reader.on('close', () => {
            if (foundCount === 0) {
                console.log(`No results found for "${keyword}".`);
            }
            resolve();
        });
    });
}

// Loop function for repeated searches
async function loopSearch() {
    while (true) {
        const keyword = await new Promise((res) => {
            rl.question("\nEnter keyword to search (or type 'exit' to quit): ", res);
        });

        if (keyword.trim().toLowerCase() === "exit") {
            console.log("Exiting program.");
            rl.close();
            break;
        }

        await searchCSV(keyword);
    }
}

// Start the loop
loopSearch();