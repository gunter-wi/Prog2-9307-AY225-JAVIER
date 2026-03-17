const fs = require('fs');

const filePath = 'Sample_Data-Prog-2-csv.csv';

fs.readFile(filePath, 'utf8', (err, data) => {
    if (err) return console.error('Error reading file:', err);

    const lines = data.split('\n').filter(line => line.trim() && !line.startsWith('Candidate'));
    lines.slice(0, 10).forEach(line => console.log(line));
});