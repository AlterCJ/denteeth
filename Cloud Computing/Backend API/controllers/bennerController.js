const { Benner } = require('../db/models');

module.exports = {
  addBanners : async(req, res, next) => {
    try {
      const { imageBenner } = req.body;
      const benner = await Benner.create({ imageBenner });
      res.status(200).json({
        error: false,
        message: 'ok',
        data: benner
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

  getBenners : async(req, res, next) => {
    try {
      const benner = await Benner.findAll();
      res.status(200).json({
        error: false,
        message: 'ok',
        data: benner
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

  deleteBenners: async(req, res, next) => {
    try {
      const { id } = req.params;
      const benner = await Benner.findOne({
        where: { id: id}
      });
      if(benner) {
        const user = await benner.destroy();
        res.status(200).json({
          error: false,
          message: 'ok',
          data: user
        });
      }else{
        return res.status(404).json({
          error: true,
          message: 'id not found',
          data: null
        });
      }
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(error);
    }
  },

  updateBenners: async(req, res, next) => {
    try {
      const { id } = req.params;
      const benner = await Benner.findOne({
        where: { id: id}
      });
      if(benner) {
        const user = await benner.update();
        res.status(200).json({
          error: false,
          message: 'ok',
          data: user
        });
      }else{
        return res.status(404).json({
          error: true,
          message: 'id not found',
          data: null
        });
      }
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(error);
    }
  }
};