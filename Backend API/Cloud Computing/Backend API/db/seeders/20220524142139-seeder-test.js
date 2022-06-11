'use strict';

module.exports = {
  async up (queryInterface, Sequelize) {
    await queryInterface.bulkInsert('Tests', [{
      image: 'gambartest.jpg',
      user:1,
      createdAt: new Date(),
      updatedAt: new Date()
     }], {});
  },

  async down (queryInterface, Sequelize) {
    await queryInterface.bulkDelete('Tests', null, {});
  }
};
