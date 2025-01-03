select p.pk_product_no, p.product_name, p.product_price, p.product_status_code,
       c.pk_category_no, c.category_gender, c.category_type,
       cr.color_name
from tbl_product p join tbl_category c
                   on p.fk_category_no = c.pk_category_no
                   join tbl_color cr
                   on p.pk_product_no = cr.fk_product_no
where product_status_code = 1
order by p.product_registerday desc;


-- categoryDAO_imple 에서 사용중인 쿼리
SELECT p.pk_product_no, p.product_name, p.product_price, i.product_image_path, i.pk_product_image_no
                FROM tbl_product p
                JOIN tbl_category c
                ON p.fk_category_no = c.pk_category_no
                JOIN tbl_color cr
                ON p.pk_product_no = cr.fk_product_no
                JOIN tbl_product_image i
                ON p.pk_product_no = i.fk_product_no
        WHERE product_status_code = 1
                AND i.pk_product_image_no = (
                SELECT MIN(pk_product_image_no)
                FROM tbl_product_image
        WHERE fk_product_no = p.pk_product_no
        );


-- 이미지 까지 포함된 쿼리
SELECT  p.pk_product_no, p.product_name, p.product_price,
        i.product_image_path, i.pk_product_image_no,
        c.pk_category_no, c.category_type,
        cr.color_name
FROM    tbl_product p
        JOIN tbl_category c
        ON p.fk_category_no = c.pk_category_no
        JOIN tbl_color cr
        ON p.pk_product_no = cr.fk_product_no
        JOIN tbl_product_image i
        ON p.pk_product_no = i.fk_product_no
WHERE   product_status_code = 1
AND     i.pk_product_image_no = (
SELECT  MIN(pk_product_image_no)
FROM    tbl_product_image
WHERE   fk_product_no = p.pk_product_no );


select *
from tbl_product_image;

select *
from tbl_category;

select *
from tbl_color;

select *
from tbl_category;


SELECT    p.pk_product_no, p.product_name, p.product_price,
          i.product_image_path, i.pk_product_image_no,
          c.category_gender
FROM      tbl_product p
          JOIN tbl_category c
          ON p.fk_category_no = c.pk_category_no
          JOIN tbl_product_image i
          ON p.pk_product_no = i.fk_product_no
WHERE     product_status_code = 1
AND       p.product_name LIKE '%후드%'
ORDER BY  p.pk_product_no asc;


SELECT p.pk_product_no, p.product_name, p.product_price
FROM tbl_product p
WHERE product_status_code = 1
AND p.product_name LIKE '%후드%';


SELECT    p.pk_product_no, p.product_name, p.product_price,
          i.product_image_path, i.pk_product_image_no,
          c.category_gender
FROM      tbl_product p
          JOIN tbl_category c
          ON p.fk_category_no = c.pk_category_no
          JOIN tbl_product_image i
          ON p.pk_product_no = i.fk_product_no
WHERE     product_status_code = 1
AND       i.pk_product_image_no = (
SELECT    MIN(pk_product_image_no)
FROM      tbl_product_image
WHERE     fk_product_no = p.pk_product_no );

-- 이미지 까지 포함된 쿼리
SELECT  p.pk_product_no, p.product_name, p.product_price,
        i.product_image_path, i.pk_product_image_no,
        c.pk_category_no, c.category_type,
        cr.color_name
FROM    tbl_product p
        JOIN tbl_category c
        ON p.fk_category_no = c.pk_category_no
        JOIN tbl_color cr
        ON p.pk_product_no = cr.fk_product_no
        JOIN tbl_product_image i
        ON p.pk_product_no = i.fk_product_no
WHERE   product_status_code = 1
AND     i.pk_product_image_no = (
SELECT  MIN(pk_product_image_no)
FROM    tbl_product_image
WHERE   fk_product_no = p.pk_product_no );



select *
from tbl_product;
