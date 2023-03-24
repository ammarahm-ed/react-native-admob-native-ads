/* eslint-disable prettier/prettier */
const { exec } = require('node:child_process')
const fs = require('fs');

console.log('Running postininstall...');

var files = fs.readdirSync('./');
for (var i = 0; i < files.length; i++) {
    var filename = files[i];
    if (filename.substring(0,29) === 'react-native-admob-native-ads' && filename.substring(filename.length - 3) === 'tgz') {
        exec('npm install ' + filename, (err) => {
            if (err) {
                console.error("could not execute command: ", err);
                return
            }
            fs.readFile('package.json', {encoding: 'utf-8'}, function(err, data) {
                if (err) throw err;
            
                let dataArray = data.split('\n');
                const searchKeyword = `"react-native-admob-native-ads": "file:..",`;
                let lastIndex = -1;
            
                for (let index=0; index<dataArray.length; index++) {
                if (dataArray[index].includes(searchKeyword)) {
                        lastIndex = index;
                        break; 
                    }
                }
            
                dataArray.splice(lastIndex, 1);
            
                const updatedData = dataArray.join('\n');
                fs.writeFile('package.json', updatedData, (err) => {
                    if (err) throw err;
                    console.log ('Successfully updated the file data');
                });
            });
            console.log('Postinstall complete.');
        });
        break;
    }
};