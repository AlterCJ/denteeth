const { User } = require('../db/models');
const jwt = require('jsonwebtoken');
const bcrypts = require('bcryptjs');

module.exports = {
  signIn : async(req, res, next) => {
    try {
      const { email, password } = req.body;
      const checkUser = await User.findOne({
        where: { email: email }
      });

      if(checkUser) {
        const checkPassword = bcrypts.compareSync(password, checkUser.password);
        if(checkPassword) {
          const token = jwt.sign({
            user: {
              id: checkUser.id,
              username: checkUser.username,
              email: checkUser.email
            }
          }, 'secret');
          res.status(200).json({
            error: false,
            message: `success signin`,
            token: token
          });
        }else{
          res.status(403).json({
            error: true,
            message: `invalid password`,
            token: null
          })
        }
      }else{
        res.status(404).json({
          error: true,
          message: `email not found`,
          token: null
        })
      }
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        token: null
      });
      next(error);
    }
  },
  signUp: async(req, res, next) => {
    try {
      const { username, email, password } = req.body;
      const checkUser = await User.findOne({
        where: { email: email }
      });
      if(checkUser) {
        return res.status(403).json({
          error: true,
          message: `email has registered ${email}`
        });
      }else{
        const user = await User.create({
          username, email, password: bcrypts.hashSync(password, 10)
        });
        res.status(200).json({
          error: false,
          message: `user created ${user.username}`
        });
      }
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`
      });
      next(error);
    }
  }
}