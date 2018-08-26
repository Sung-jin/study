const Messages = require('../model/message');
class SocketHander {

  constructor() {
    this.db;
  }

  connect() {
    this.db = require('mongoose').connect('mongodb://localhost:27017/chat', { useNewUrlParser: true});
    this.db.Promise = global.Promise;
  }

  close() {
    return this.db;
  }

  getMessages() {
    return Messages.find();
  }

  storeMessages(data) {
    const newMessages = new Messages({
      name: data.name,
      msg: data.msg
    });

    const doc = newMessages.save();
  }
}

module.exports = SocketHander;
