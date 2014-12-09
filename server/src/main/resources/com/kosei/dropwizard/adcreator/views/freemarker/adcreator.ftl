<#-- @ftlvariable name="" type="com.kosei.dropwizard.adcreator.views.AdCreatorView" -->
<html>
<head>
    <script>
        function doit(functionName) {
            var title = document.getElementById('title').value;
            var body = document.getElementById('body').value;
            var price = document.getElementById('price').value;


            var templateId = "__embedded";


            var productImageFile = document.getElementById('productImage').value;
            var logoImageFile = document.getElementById('logoImage').value;
            var callToActionImageFile = document.getElementById('callToActionImage').value;

            var headerFont = document.getElementById('headerFont').value;
            var headerFontSize = document.getElementById('headerFontSize').value;
            var headerFontWeight = document.getElementById('headerFontWeight').value;
            var headerFontColor = document.getElementById('headerFontColor').value;

            var descriptionFont = document.getElementById('descriptionFont').value;
            var descriptionFontSize = document.getElementById('descriptionFontSize').value;
            var descriptionFontWeight = document.getElementById('descriptionFontWeight').value;
            var descriptionFontColor = document.getElementById('descriptionFontColor').value;

            var priceFont = document.getElementById('priceFont').value;
            var priceFontSize = document.getElementById('priceFontSize').value;
            var priceFontWeight = document.getElementById('priceFontWeight').value;
            var priceFontColor = document.getElementById('priceFontColor').value;

            var backgroundColor = document.getElementById('backgroundColor').value;

            var productHeight = document.getElementById('productHeight').value;
            var productWidth = document.getElementById('productWidth').value;
            var productOriginX = document.getElementById('productOriginX').value;
            var productOriginY = document.getElementById('productOriginY').value;
            var titleHeight = document.getElementById('titleHeight').value;
            var titleWidth = document.getElementById('titleWidth').value;
            var titleOriginX = document.getElementById('titleOriginX').value;
            var titleOriginY = document.getElementById('titleOriginY').value;
            var descriptionHeight = document.getElementById('descriptionHeight').value;
            var descriptionWidth = document.getElementById('descriptionWidth').value;
            var descriptionOriginX = document.getElementById('descriptionOriginX').value;
            var descriptionOriginY = document.getElementById('descriptionOriginY').value;
            var priceHeight = document.getElementById('priceHeight').value;
            var priceWidth = document.getElementById('priceWidth').value;
            var priceOriginX = document.getElementById('priceOriginX').value;
            var priceOriginY = document.getElementById('priceOriginY').value;
            var calltoactionHeight = document.getElementById('calltoactionHeight').value;
            var calltoactionWidth = document.getElementById('calltoactionWidth').value;
            var calltoactionOriginX = document.getElementById('calltoactionOriginX').value;
            var calltoactionOriginY = document.getElementById('calltoactionOriginY').value;

            var logoHeight = document.getElementById('logoHeight').value;
            var logoWidth = document.getElementById('logoWidth').value;
            var logoOriginX = document.getElementById('logoOriginX').value;
            var logoOriginY = document.getElementById('logoOriginY').value;


            var mainHeight = document.getElementById('mainHeight').value;
            var mainWidth = document.getElementById('mainWidth').value;
            var mainOriginX = document.getElementById('mainOriginX').value;
            var mainOriginY = document.getElementById('mainOriginY').value;
            var templateDefName = document.getElementById('templateDefName').value;

            if (functionName == "loadtemplate") {
                templateDefName = document.getElementById('availableTemplates').value;
            }


            var url = "/adcreator/" + functionName + "?productImage=" + productImageFile + "&logoImage=" + logoImageFile +
                    "&callToActionImage=" + callToActionImageFile +
                    "&headerFont=" + headerFont +
                    "&headerFontSize=" + headerFontSize + "&headerFontWeight=" + headerFontWeight + "&headerFontColor=" + headerFontColor +
                    "&priceFont=" + priceFont +
                    "&priceFontSize=" + priceFontSize + "&priceFontWeight=" + priceFontWeight + "&priceFontColor=" + priceFontColor +
                    "&priceText=" + price +
                    "&descriptionFont=" + descriptionFont + "&descriptionFontSize=" + descriptionFontSize + "&descriptionFontWeight=" + descriptionFontWeight +
                    "&descriptionFontColor=" + descriptionFontColor +
                    "&descriptionText=" + body + "&headerText=" + title + "&backgroundColor=" + backgroundColor + "&templateId=" + templateId +
                    "&productHeight=" + productHeight + "&productWidth=" + productWidth + "&productOriginX=" + productOriginX + "&productOriginY=" + productOriginY +
                    "&titleHeight=" + titleHeight + "&titleWidth=" + titleWidth + "&titleOriginX=" + titleOriginX + "&titleOriginY=" + titleOriginY +
                    "&descriptionHeight=" + descriptionHeight + "&descriptionWidth=" + descriptionWidth + "&descriptionOriginX=" + descriptionOriginX + "&descriptionOriginY=" + descriptionOriginY +
                    "&priceHeight=" + priceHeight + "&priceWidth=" + priceWidth + "&priceOriginX=" + priceOriginX + "&priceOriginY=" + priceOriginY +
                    "&calltoactionHeight=" + calltoactionHeight + "&calltoactionWidth=" + calltoactionWidth + "&calltoactionOriginX=" + calltoactionOriginX + "&calltoactionOriginY=" + calltoactionOriginY +
                    "&logoHeight=" + logoHeight + "&logoWidth=" + logoWidth + "&logoOriginX=" + logoOriginX + "&logoOriginY=" + logoOriginY +
                    "&mainHeight=" + mainHeight + "&mainWidth=" + mainWidth + "&mainOriginX=" + mainOriginX + "&mainOriginY=" + mainOriginY +
                    "&templateDefName=" + templateDefName;


            window.location.href = url;
        }

        function createAd() {
            doit("create");
        }


        function saveTemplate() {
            doit("savetemplate");
        }

        function loadTemplate() {
            doit("loadtemplate");
        }
    </script>
</head>
<body bgcolor="#F5F5F5">

<h1>Create an Image</h1>

<form>


    <input type="button" value="Submit" onclick="createAd()"><br>
    Price Text: <input type="text" name="price" id="price" size="60"  <#if priceText??> value="${priceText}"</#if>/>
    <br/>
    Header Text: <input type="text" name="title" id="title" size="60"  <#if headerText??> value="${headerText}"</#if>/>
    <br/>

    Description: <input type="text" name="body" id="body" size="60" <#if descriptionText??>
                        value="${descriptionText}"</#if>/>


    <br/>

    <select id="productImage">
        <option value="">-- product images --</option>

    <#list products as product>
        <option value="${product}"  <#if selectedProduct(product)??> selected</#if>>${product}</option>
    </#list>
    </select>

    <select id="logoImage">
        <option value="">-- logo images --</option>

    <#list logos as logo>
        <option value="${logo}" <#if selectedLogo(logo)??> selected</#if>>${logo}</option>
    </#list>
    </select>

    <select id="callToActionImage">
        <option value="">-- callToAction images --</option>

    <#list callToActions as callToAction>
        <option value="${callToAction}" <#if selectedCallToAction(callToAction)??>
                selected</#if>>${callToAction}</option>
    </#list>
    </select>

    <br/>
    Header Font<br/>
    <select id="headerFont">
        <option value="">-- header fonts --</option>

    <#list fonts as font>
        <option value="${font}" <#if selectedHeaderFont(font)??> selected</#if>>${font}</option>
    </#list>
    </select>

    <select id="headerFontSize">
        <option value="">-- header size --</option>

    <#list sizes as size>
        <option value="${size}" <#if selectedHeaderFontSize(size)??> selected</#if>>${size}</option>
    </#list>
    </select>

    <select id="headerFontWeight">
        <option value="">-- header weight --</option>

    <#list weights as weight>
        <option value="${weight}" <#if selectedHeaderFontWeight(weight)??> selected</#if>>${weight}</option>
    </#list>
    </select>

    Font Color: <input type="text" name="headerFontColor" id="headerFontColor" size="60" <#if headerFontColor??>
                       value="${headerFontColor}"</#if>/>

    <br/>
    Description Font<br/>
    <select id="descriptionFont">
        <option value="">-- description fonts --</option>

    <#list fonts as font>
        <option value="${font}" <#if selectedDescriptionFont(font)??> selected</#if>>${font}</option>
    </#list>
    </select>

    <select id="descriptionFontSize">
        <option value="">-- description size --</option>

    <#list sizes as size>
        <option value="${size}" <#if selectedDescriptionFontSize(size)??> selected</#if>>${size}</option>
    </#list>
    </select>

    <select id="descriptionFontWeight">
        <option value="">-- description weight --</option>

    <#list weights as weight>
        <option value="${weight}" <#if selectedDescriptionFontWeight(weight)??> selected</#if>>${weight}</option>
    </#list>
    </select>

    Font Color: <input type="text" name="descriptionFontColor" id="descriptionFontColor"
                       size="60" <#if descriptionFontColor??> value="${descriptionFontColor}"</#if>/>


    <br/>
    Price Font<br/>
    <select id="priceFont">
        <option value="">-- price fonts --</option>

    <#list fonts as font>
        <option value="${font}" <#if selectedPriceFont(font)??> selected</#if>>${font}</option>
    </#list>
    </select>

    <select id="priceFontSize">
        <option value="">-- price size --</option>

    <#list sizes as size>
        <option value="${size}" <#if selectedPriceFontSize(size)??> selected</#if>>${size}</option>
    </#list>
    </select>

    <select id="priceFontWeight">
        <option value="">-- price weight --</option>

    <#list weights as weight>
        <option value="${weight}" <#if selectedPriceFontWeight(weight)??> selected</#if>>${weight}</option>
    </#list>
    </select>

    Font Color: <input type="text" name="priceFontColor" id="priceFontColor" size="60" <#if priceFontColor??>
                       value="${priceFontColor}"</#if>/>


    <br/>
    Background Color: <input type="text" name="backgroundColor" id="backgroundColor" size="60"  <#if backgroundColor??>
                             value="${backgroundColor}"</#if>/> <br/>


</form>

<br/>

<#if adCreator.imageUrl?has_content>
<img src="${adCreator.imageUrl}"/>
</#if>


<br/>
<br/>
<hr>
<h1>Template Definition (Embedded)</h1>


<br/>

<br/>
Template Name: <input type="text" name="templateDefName" id="templateDefName" size="60"  <#if templateDefName??>
                      value="${templateDefName}"</#if>/>
<input type="button" value="Save Template" onclick="saveTemplate()">
<input type="button" value="Load Template" onclick="loadTemplate()">

<select id="availableTemplates">
    <option value="">-- available templates --</option>

<#list availableTemplates as availableTemplate>
    <option value="${availableTemplate}" <#if selectedAvailableTemplate(availableTemplate)??> selected</#if>>${availableTemplate}</option>
</#list>
</select>


<br/>


<br/>

<h3> All are Bounding box (heigth, width, origin_x, origin_y </h3>
<br/>


<table>
    <tr>
        Template Bounding Box : <input type="text" name="mainHeight" id="mainHeight" size="4"  <#if mainHeight??>
                                       value="${mainHeight}"</#if>/>
        <input type="text" name="mainWidth" id="mainWidth" size="4"  <#if mainWidth??> value="${mainWidth}"</#if>/>
        <input type="hidden" name="mainOriginX" id="mainOriginX" value="0"/>
        <input type="hidden" name="mainOriginY" id="mainOriginY" value="0"/> <br/>
    </tr>
    <tr>
        <td>Title Text :
        <td><input type="text" name="titleHeight" id="titleHeight" size="4"  <#if titleHeight??>
                   value="${titleHeight}"</#if>/></td>
        <td><input type="text" name="titleWidth" id="titleWidth" size="4"  <#if titleWidth??>
                   value="${titleWidth}"</#if>/></td>
        <td><input type="text" name="titleOriginX" id="titleOriginX" size="4"  <#if titleOriginX??>
                   value="${titleOriginX}"</#if>/></td>
        <td><input type="text" name="titleOriginY" id="titleOriginY" size="4"  <#if titleOriginY??>
                   value="${titleOriginY}"</#if>/></td>
    </tr>
    <tr>
        <td>Product Image :
        <td><input type="text" name="productHeight" id="productHeight" size="4"  <#if productHeight??>
                   value="${productHeight}"</#if>/></td>
        <td><input type="text" name="productWidth" id="productWidth" size="4"  <#if productWidth??>
                   value="${productWidth}"</#if>/></td>
        <td><input type="text" name="productOriginX" id="productOriginX" size="4"  <#if productOriginX??>
                   value="${productOriginX}"</#if>/></td>
        <td><input type="text" name="productOriginY" id="productOriginY" size="4"  <#if productOriginY??>
                   value="${productOriginY}"</#if>/></td>
    </tr>
    <tr>
        <td>Description Text :
        <td><input type="text" name="descriptionHeight" id="descriptionHeight" size="4"  <#if descriptionHeight??>
                   value="${descriptionHeight}"</#if>/></td>
        <td><input type="text" name="descriptionWidth" id="descriptionWidth" size="4"  <#if descriptionWidth??>
                   value="${descriptionWidth}"</#if>/></td>
        <td><input type="text" name="descriptionOriginX" id="descriptionOriginX" size="4"  <#if descriptionOriginX??>
                   value="${descriptionOriginX}"</#if>/></td>
        <td><input type="text" name="descriptionOriginY" id="descriptionOriginY" size="4"  <#if descriptionOriginY??>
                   value="${descriptionOriginY}"</#if>/></td>
    </tr>
    <tr>
        <td>Price Text :
        <td><input type="text" name="priceHeight" id="priceHeight" size="4"  <#if priceHeight??>
                   value="${priceHeight}"</#if>/></td>
        <td><input type="text" name="priceWidth" id="priceWidth" size="4"  <#if priceWidth??>
                   value="${priceWidth}"</#if>/></td>
        <td><input type="text" name="priceOriginX" id="priceOriginX" size="4"  <#if priceOriginX??>
                   value="${priceOriginX}"</#if>/></td>
        <td><input type="text" name="priceOriginY" id="priceOriginY" size="4"  <#if priceOriginY??>
                   value="${priceOriginY}"</#if>/></td>
    </tr>
    <tr>
        <td>Call Action Image :
        <td><input type="text" name="calltoactionHeight" id="calltoactionHeight" size="4"  <#if calltoactionHeight??>
                   value="${calltoactionHeight}"</#if>/></td>
        <td><input type="text" name="calltoactionWidth" id="calltoactionWidth" size="4"  <#if calltoactionWidth??>
                   value="${calltoactionWidth}"</#if>/></td>
        <td><input type="text" name="calltoactionOriginX" id="calltoactionOriginX" size="4"  <#if calltoactionOriginX??>
                   value="${calltoactionOriginX}"</#if>/></td>
        <td><input type="text" name="calltoactionOriginY" id="calltoactionOriginY" size="4"  <#if calltoactionOriginY??>
                   value="${calltoactionOriginY}"</#if>/></td>
    </tr>
    <tr>
        <td>Logo Image :
        <td><input type="text" name="logoHeight" id="logoHeight" size="4"  <#if logoHeight??>
                   value="${logoHeight}"</#if>/></td>
        <td><input type="text" name="logoWidth" id="logoWidth" size="4"  <#if logoWidth??> value="${logoWidth}"</#if>/>
        </td>
        <td><input type="text" name="logoOriginX" id="logoOriginX" size="4"  <#if logoOriginX??>
                   value="${logoOriginX}"</#if>/></td>
        <td><input type="text" name="logoOriginY" id="logoOriginY" size="4"  <#if logoOriginY??>
                   value="${logoOriginY}"</#if>/></td>
    </tr>
</table>


</body>
</html>