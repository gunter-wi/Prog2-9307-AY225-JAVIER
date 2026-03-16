const fs = require('fs');
const csv = require('csv-parser');

const filePath = 'Sample_Data-Prog-2-csv.csv'; // your CSV file
const keyword = 'Python'; // change to any word you want to search
let records = [];

// Load dataset
fs.createReadStream(filePath)
  .pipe(csv())
  .on('data', (row) => {
    records.push(row);
  })
  .on('end', () => {
    // MP01 – Total number of records
    console.log(`Total number of records: ${records.length}`);

    // MP02 – First 10 rows
    console.log('\nFirst 10 rows:');
    console.log(records.slice(0, 10));

    // MP03 – Search for a keyword
    const searchResults = records.filter(row =>
      Object.values(row).some(val => val.toLowerCase().includes(keyword.toLowerCase()))
    );

    console.log(`\nRecords containing '${keyword}':`);
    console.log(searchResults);
  });