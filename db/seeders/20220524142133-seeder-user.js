'use strict';
const bcrypt = require('bcryptjs');

module.exports = {
  async up (queryInterface, Sequelize) {
    const password = bcrypt.hashSync('rahasia', 10);
     await queryInterface.bulkInsert('Users', 
     [
        {
          id:1,
          username: 'John Doe',
          password: password,
          email: 'admin@gmail.com',
          createdAt: new Date(),
          updatedAt: new Date()
        }
      ], 
    {});
  
  },

  async down (queryInterface, Sequelize) {
     await queryInterface.bulkDelete('Users', null, {});
  }
};
