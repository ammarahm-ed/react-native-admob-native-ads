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
            console.log('Postinstall complete.');
        });
        break;
    }
};