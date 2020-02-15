const functions = require('firebase-functions');
const nodemailer = require('nodemailer');



const mailTransport = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: "your_email_address",
    pass: "your_email_password",
  },
});



// Sends an email confirmation when a user create account on the app.
exports.sendEmailConfirmation = functions.database.ref('/users/{uid}').onCreate(async (change) => {
    const val = change.val();


    console.log("email "+val.email);
    console.log("Name  "+val.full_name);
  
    const mailOptions = {
      from: '"Codingwithset." <EAR@ayetolusamuel@gmail.com>',
      to: val.email,
    };
  
    // Building Email message.
    mailOptions.subject =   "EAR Notification";
    mailOptions.html =  `
    <img src="https://bit.ly/2UAujkP" alt="Girl in a jacket" style="width:300px;height:300px;">
    <h1><br>Thanks for resgistry with us.  </h1> 
    <b><i> ${val.full_name} </b></i> 
    <p>our goal is to serve you better.</p> `;
    
    try {
      await mailTransport.sendMail(mailOptions);
      console.log(`New ${val.email}confirmation email sent to:`, val.email);
    } catch(error) {
      console.error('There was an error while sending the email:', error);
    }
    return null;
  });