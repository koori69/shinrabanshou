/**
 * Created by Kris on 2015/1/30.
 */
var express = require('express');
var crypto = require('crypto');
var router = express.Router();
var User = require('../models/user.js');
var Trans = require("../models/trans.js");
var loanApp = require("../models/loanapp.js");
var http = require("http");

function checkLogin(req, res, next) {
    if (!req.session.user) {
        req.flash('error', '未登录');
        return res.redirect('/login');
    }
    next();
}

function checkNotLogin(req, res, next) {
    if (req.session.user) {
        req.flash('error', '已登录');
        return res.redirect('/');
    }
    next();
}

function summaries(transHistory) {
    var totalFund = {};
    for (var index = 0; index < transHistory.length; index ++) {
        history = transHistory[index];
        console.log(history);
        console.log(!totalFund[history["owner"]]);
        if (!totalFund[history["owner"]]) {
            totalFund[history["owner"]] = 0;
        }

        if (history["type"] == "IN") {
            totalFund[history["owner"]] += Number(history["amount"]);
        } else {
            totalFund[history["owner"]] -= Number(history["amount"]);
        }
    }
    console.log(totalFund);
    return totalFund;
}

router.post("/login", function (req,res) {
    //生成口令的散列值
    var md5 = crypto.createHash('md5');
    var password = md5.update(req.body.password).digest('base64');

    User.get(req.body.username, function(err, user) {
        if (!user) {
            res.end(JSON.stringify({"suc":false,"msg":"用户不存在"}));
        }
        else if (user.password != password) {
            res.end(JSON.stringify({"suc":false,"msg":"密码错误"}));
        }
        else{
            req.session.user = user;
            res.end(JSON.stringify({"suc":true,"msg":"登录成功","data":JSON.stringify(user)}));
        }

    });
});

module.exports = router;