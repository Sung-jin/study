const mongoose = require('mongoose');

const messagesSchema = mongoose.Schema({
  name: String,
  msg: String
});

module.exports = mongoose.model('Messages', messagesSchema);
