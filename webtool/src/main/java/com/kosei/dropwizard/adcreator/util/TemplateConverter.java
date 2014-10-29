package com.kosei.dropwizard.adcreator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lanceriedel on 10/22/14.
 */
public class TemplateConverter {
   //{"title":{"height":210,"width":23,"origin_x":55,"origin_y":8},"logo":{"height":150, "width":150,"origin_x":160, "origin_y":0},"description":{"height":210,"width":20,"origin_x":55,"origin_y":28},"price":{"height":210,"width":20,"origin_x":55,"origin_y":228}, "product":{"height":45,"width":45,"origin_x":2,"origin_y":2},"main":{"height":320,"width":320,"origin_x":0,"origin_y":0},"calltoaction":{"height":224,"width":64,"origin_x":90,"origin_y":260}}



    public static String convert(Map<String,Object> in) throws Exception {
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(in);
        return json;
    }

    public static class TemplateRecord {
        public int height;
        public int width;
        public int origin_x;
        public int origin_y;
        public TemplateRecord(int width, int height, int origin_x, int origin_y) {
            this.height = height;
            this.width = width;
            this.origin_x = origin_x;
            this.origin_y = origin_y;

        }
    }

    public static void main(String args[]) throws Exception {

        /**
         *
         * //PREDEFINED TEMPLATES
         else if (templateId.compare("template_1") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
         product = ImageEntry("valid", 45, 45, 2, 2);

         description = TextEntry(descriptionFontJson, 210, 20, 55, 28);
         title = TextEntry(titleFontJson, 210, 23, 55, 8);
         */

        Map<String,Object> template_1 = new HashMap<String, Object>();
        template_1.put("name", "template_1");

        template_1.put("title",new TemplateRecord(210, 23, 55, 8));
        template_1.put("logo", new TemplateRecord(50, 50, 320 - 50, 0));
        template_1.put("product", new TemplateRecord( 45, 45, 2, 2));
        template_1.put("description",new TemplateRecord(210, 20, 55, 28));
        template_1.put("main",new TemplateRecord(320, 50, 0, 0));
        template_1.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_1.put("price",new TemplateRecord(0, 0, 0, 0));

        System.out.println(convert(template_1));
        /*
         } else if (templateId.compare("template_2") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry(logoFilename, 50, 50, 0, 0);
         product = ImageEntry("valid", 45, 45, 320 - 45 - 2, 2);

         description = TextEntry(descriptionFontJson, 210, 20, 55, 28);
         title = TextEntry(titleFontJson, 210, 23, 55, 8);
         }

         */
        Map<String,Object> template_2 = new HashMap<String, Object>();
        template_2.put("name", "template_2");

        template_2.put("title",new TemplateRecord(210, 23, 55, 8));
        template_2.put("logo", new TemplateRecord(50, 50, 0, 0));
        template_2.put("product", new TemplateRecord( 45, 45, 320 - 45 - 2, 2));
        template_2.put("description",new TemplateRecord(210, 20, 55, 28));
        template_2.put("main",new TemplateRecord(320, 50, 0, 0));
        template_2.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_2.put("price",new TemplateRecord(0, 0, 0, 0));

        System.out.println(convert(template_2));

        /*
        else if (templateId.compare("template_3") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
         product = ImageEntry("invalid", -1, -1, -1, -1);
         description = TextEntry(descriptionFontJson, 250, 20, 10, 28);
         title = TextEntry(titleFontJson, 250, 25, 10, 8);
            */
        Map<String,Object> template_3 = new HashMap<String, Object>();
        template_3.put("name", "template_3");

        template_3.put("title",new TemplateRecord(250, 25, 10, 8));
        template_3.put("logo", new TemplateRecord(50, 50, 320 - 50, 0));
        template_3.put("product", new TemplateRecord( 0,0,0,0));
        template_3.put("description",new TemplateRecord(250, 20, 10, 28));
        template_3.put("main",new TemplateRecord(320, 50, 0, 0));
        template_3.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_3.put("price",new TemplateRecord(0, 0, 0, 0));

        System.out.println(convert(template_3));

        /*

         } else if (templateId.compare("template_4") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry("invalid", -1, -1, -1, -1);
         product = ImageEntry("valid", 45, 45, 320 - 45 - 2, 2);
         description = TextEntry(descriptionFontJson, 250 ,20 , 10, 28);
         title = TextEntry(titleFontJson, 250, 25 , 10, 8);

         */
        Map<String,Object> template_4 = new HashMap<String, Object>();
        template_4.put("name", "template_4");

        template_4.put("title",new TemplateRecord(250, 25, 10, 8));
        template_4.put("logo", new TemplateRecord(0,0,0, 0));
        template_4.put("product", new TemplateRecord( 0,0,0,0));
        template_4.put("description",new TemplateRecord(250, 20, 10, 28));
        template_4.put("main",new TemplateRecord(320, 50, 0, 0));
        template_4.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_4.put("price",new TemplateRecord(0, 0, 0, 0));

        System.out.println(convert(template_4));


        /*
         } else if (templateId.compare("template_5") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
         product = ImageEntry("valid", 45, 45, 320 - 50 - 45 - 2, 2);

         description = TextEntry(descriptionFontJson, 210, 20, 10, 28);
         title = TextEntry(titleFontJson, 210, 23, 10, 8);
         isTemplateSet = true;

         }

         **/

        Map<String,Object> template_5 = new HashMap<String, Object>();
        template_5.put("name", "template_5");

        template_5.put("title",new TemplateRecord(250, 25, 10, 8));
        template_5.put("logo", new TemplateRecord(50, 50, 320 - 50, 0));
        template_5.put("product", new TemplateRecord(45, 45, 320 - 50 - 45 - 2, 2));
        template_5.put("description",new TemplateRecord(250, 20, 10, 28));
        template_5.put("main",new TemplateRecord(320, 50, 0, 0));
        template_5.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_5.put("price",new TemplateRecord(0, 0, 0, 0));
        System.out.println(convert(template_5));


        /**

         } else if (templateId.compare("template_6") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry(logoFilename, 50, 50, 0, 0);
         product = ImageEntry("invalid", -1, -1, -1, -1);

         description = TextEntry(descriptionFontJson, 250, 20, 55, 28);
         title = TextEntry(titleFontJson, 250, 25, 55, 8);
         }
         **/



        Map<String,Object> template_6 = new HashMap<String, Object>();
        template_6.put("name", "template_6");

        template_6.put("title",new TemplateRecord(250, 25, 55, 8));
        template_6.put("logo", new TemplateRecord(50, 50, 0, 0));
        template_6.put("product", new TemplateRecord(0,0,0,0));
        template_6.put("description",new TemplateRecord(250, 20, 55, 28));
        template_6.put("main",new TemplateRecord(320, 50, 0, 0));
        template_6.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_6.put("price",new TemplateRecord(0, 0, 0, 0));

        System.out.println(convert(template_6));


        /**
         else if (templateId.compare("template_7") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry("invalid", -1, -1, -1, -1);
         product = ImageEntry("valid", 45, 45, 2, 2);

         description = TextEntry(descriptionFontJson, 250, 20, 55, 28);
         title = TextEntry(titleFontJson, 250, 25, 55, 8);
         isTemplateSet = true;
         }  **/


        Map<String,Object> template_7 = new HashMap<String, Object>();
        template_7.put("name", "template_7");

        template_7.put("title",new TemplateRecord(250, 25, 55, 8));
        template_7.put("logo", new TemplateRecord(50, 50, 320 - 50, 0));
        template_7.put("product", new TemplateRecord( 45, 45, 2, 2));
        template_7.put("description",new TemplateRecord(250, 20, 10, 28));
        template_7.put("main",new TemplateRecord(320, 50, 0, 0));
        template_7.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_7.put("price",new TemplateRecord(0, 0, 0, 0));
        System.out.println(convert(template_7));

        /**else if (templateId.compare("template_8") == 0) {
         size_x = 320;
         size_y = 50;
         background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
         logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
         product = ImageEntry("valid", 45, 45, 2, 2);

         title = TextEntry(titleFontJson, 210, 40, 55, 7);
         description = TextEntry();
         }
         */

        Map<String,Object> template_8 = new HashMap<String, Object>();
        template_8.put("name", "template_8");

        template_8.put("title",new TemplateRecord(210, 40, 55, 7));
        template_8.put("logo", new TemplateRecord(50, 50, 320 - 50, 0));
        template_8.put("product", new TemplateRecord( 45, 45, 2, 2));
        template_8.put("description",new TemplateRecord(250, 20, 10, 28));
        template_8.put("main",new TemplateRecord(320, 50, 0, 0));
        template_8.put("calltoaction",new TemplateRecord(0, 0, 0, 0));
        template_8.put("price",new TemplateRecord(0, 0, 0, 0));


        System.out.println(convert(template_8));

    }
}
