/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const { onCall } = require("firebase-functions/v2/https");
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


// if deviceId present in registered devices then return true else return false
exports.isLoggedIn = onCall( async (req)=> {
    
    const deviceId = req.data.deviceId;

    const user = await db.collection("registered-devices").doc(deviceId).get();

    if (user.exists){
        return true;
    }

    return false;
});


// if team exist in teams create the document of user and add it to registered-devices and return 
// else return empty object
/*

deviceId: {
    level: 0, //default, first time registering
    currentQuestion: "1" //default, first time registering
    team: teamId //got in request object
    score: 0 //default, first time registering
    questionScore: { //default 0 for all questions, get the questions from the questions collection
        1: 0,
        2: 0,
        .
        .
        .
    }
}

*/
exports.login = onCall( async (req)=> {

    const teamId = req.data.teamId;
    const deviceId = req.data.deviceId;

    const team = await db.collection("teams").doc(teamId).get();

    if (team.exists){
        const user = {
            level: 0,
            currentQuestion: "1",
            team: teamId,
            score: 0,
            questionScore: {

            }
        };

        const questionCount = await db.collection("questions").count().get();

        for (let questionNumber = 1; questionNumber <= questionCount.data().count; questionNumber++){
            user.questionScore[`${questionNumber}`] = 0;
        }

        const userDocRef = db.collection("registered-devices").doc(deviceId);
        await userDocRef.set(user);

        return team.data();
    }

    return {};
});
