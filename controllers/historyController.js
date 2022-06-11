const { History } = require('../db/models');

module.exports = {
  addHistory: async(req, res, next) => {
    try {
      const { status } = req.body;
      const history = await History.create({
        status, userId: req.user.id
     })
     res.status(200).json({
       error: false,
       message: `success save data`,
       data: history
     })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
      })
    }

},
    getAllHistory: async(req, res, next) => {
    try {
      const checkHistory = await History.findAll({
        where: { userId: req.user.id},
        order: [
          ['id', 'DESC']
        ]
      });
      res.status(200).json({
        error: false,
        message: 'success get all history',
        data: checkHistory
      })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(null);
    }
  }
}

