const { Profile } = require('../db/models');

module.exports = {
  getDetailProfile: async(req, res, next) => {
    try {
      console.log(req.user);
      const id = req.user.id;
      const profile = await Profile.findOne({
        where: { id: id}
      });
      if(!profile) {
        return res.status(404).json({
          error: true,
          message: `id not found`,
          data: null
        });
      };
      res.status(200).json({
        error: false,
        message: 'ok',
        data: profile
      })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(error)
    }
  },

  createProfile: async(req, res, next) => {
    try {
      console.log(req.user);
      const { imageprofile, fullname, data, location, gender } = req.body;
      const profile = await Profile.create({
      imageprofile, username: req.user.username, fullname, email: req.user.email, data, location, gender 
      });
      res.status(200).json({
        error: false,
        message: 'ok',
        data: profile
      })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(error)
    }
  },

  updateProfile: async(req, res, next) => {
    try {
      const id = req.user.id;
      const { imageprofile, fullname, data, location, gender } = req.body;
      const checkId = await Profile.findOne({
        where: { id: id}
      });
      if(checkId) {
        const profile = await checkId.update({
          id: id, imageprofile, username: req.user.username, fullname, email: req.user.email, data, location, gender 
       });
       res.status(200).json({
         error: false,
         message: 'ok',
         data: profile
       })
      }else{
        return res.status(404).json({
          error: false,
          message: `id not found`,
          data: null
        })
      }
      
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(error)
    }
  }
}