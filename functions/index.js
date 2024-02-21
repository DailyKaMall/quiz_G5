/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const { onRequest, onCall, HttpsError } = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");
const { initializeApp } = require("firebase-admin/app");
const { getFirestore } = require("firebase-admin/firestore");

initializeApp();

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

const db = getFirestore();

async function quizExists(){
    // let quiz = await 
}

// check if quiz exist if no return false if yes then check if device id is present in registered-participants
//   if yes return true else return false

exports.isLoggedIn = onCall( async req => {

    const deviceId = req.data.deviceId;

    return true;
});

// check if first character is ? then check admin if admin ok otherwise not authorized
// if not ? then check if any quiz running if not running no quiz at this time else check password in quiz teams
//    if not found 

exports.login = onCall( async req => {

    const teamId = req.data.teamId;

    let team = await db.collection("teams").doc(teamId).get();

    if (!team.exists){
        return {};
    }

    return team.data();
});
