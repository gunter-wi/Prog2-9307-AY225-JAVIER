const fs = require('fs');

const filePath = 'results.csv';
const keyword = 'JavaScript';

fs.readFile(filePath, 'utf8', (err, data) => {
    if (err) return console.error('Error reading file:', err);

    const lines = data.split('\n').filter(line => line.includes(keyword));
    if (lines.length > 0) {
        lines.forEach(line => console.log(line));
    } else {
        console.log(`No records found containing: ${keyword}`);
    }
});