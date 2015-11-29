#!/bin/bash
find $1*.jpg -exec convert convert -verbose -colorspace RGB -resize 2500 -interlace none -density 800 -quality 100 {} {}.pdf \;
find $1*.jpg.pdf | xargs gs -q -dNOPAUSE -dBATCH -sDEVICE=pdfwrite -sOutputFile=$2.pdf ;
rm $1*.jpg.pdf ;
