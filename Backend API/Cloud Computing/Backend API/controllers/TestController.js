const { Test } = require('../db/models');

module.exports = {
  getAllTest: async (req, res, next) => {
    try {
      const test = await Test.findAll();
      res.status(200).json({
        error: false,
        message: `ok`,
        data: test
      })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(error);
    }
  },

  addTest: async(req, res, next) => {
    try {
      if(!req.file) {
        return res.status(403).json({
          error: true,
          message: `error`,
          data: null
        });
      }
      res.status(200).json({
        error: false,
        message: 'success upload image',
        data: { src: `/uploads/${req.file.filename}`}
      })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(error);
    }
  }
}