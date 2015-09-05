// #!/bin/bash
// # lessc -x style.less > style.css
// # lessc style.less > style.css

// gulp build-less：你会在目录下发现less目录下的less文件被编译成对应的css文件。
// gulp min-styles：会在css目录下输出all.css和all.min.css文件。
// gulp develop：会监听所有less文件，当有less文件改变时，会执行build-less和min-styles
var gulp = require('gulp'),
		less = require('gulp-less'),
		concat = require('gulp-concat'),
		rename = require('gulp-rename'),
		minifycss = require('gulp-minify-css');

var pathLess = "./src/main/less/";
var pathCss = "./src/main/webapp/styles/";
var pathJs = "./src/main/javascript/";

var pathLessWorkout = pathLess + "workout/";
var pathCssWorkout  = pathCss  + "workout/";
var pathJsWorkout   = pathJs   + "workout/";

// less编译为css
gulp.task('build-less', function() {
	gulp.src(pathLessWorkout + '*.less')
		.pipe(less({ compress: true }))
		.on('error', function(e) {console.log(e);} )
		.pipe(gulp.dest(pathCssWorkout));
	});

// 合并、压缩、重命名css
gulp.task('min-styles', ['build-less'], function() {
	gulp.src([pathCssWorkout + '*.css'])
		.pipe(concat('workout.css'))      // 合并文件为all.css
		.pipe(gulp.dest(pathCssWorkout))  // 输出all.css文件
		.pipe(rename({ suffix: '.min' })) // 重命名all.css为 all.min.css
		.pipe(minifycss())                // 压缩css文件
		.pipe(gulp.dest(pathCssWorkout)); // 输出all.min.css
	});

gulp.task('develop', function() {
	gulp.watch('./public/less/*.less', ['build-less', 'min-styles']);
	});

