<#-- @ftlvariable name="" type="com.kosei.dropwizard.adcreator.views.AdCreatorView" -->
<html>
<head>
    <script>
        function doit() {
            var title = document.getElementById('title').value;
            var body = document.getElementById('body').value;

            var templateId = document.getElementById('templateId').value;


            var productImageFile = document.getElementById('productImage').value;
            var logoImageFile = document.getElementById('logoImage').value;
            var headerFont = document.getElementById('headerFont').value;
            var headerFontSize = document.getElementById('headerFontSize').value;
            var headerFontWeight = document.getElementById('headerFontWeight').value;
            var headerFontColor = document.getElementById('headerFontColor').value;

            var descriptionFont = document.getElementById('descriptionFont').value;
            var descriptionFontSize = document.getElementById('descriptionFontSize').value;
            var descriptionFontWeight = document.getElementById('descriptionFontWeight').value;
            var descriptionFontColor = document.getElementById('descriptionFontColor').value;

            var backgroundColor = document.getElementById('backgroundColor').value;


            var url =  "/adcreator/create?productImage=" + productImageFile +"&logoImage="+logoImageFile+ "&headerFont="+headerFont+
                   "&headerFontSize="+headerFontSize+"&headerFontWeight="+headerFontWeight+"&headerFontColor="+headerFontColor+
                    "&descriptionFont="+descriptionFont+"&descriptionFontSize=" + descriptionFontSize +"&descriptionFontWeight="+descriptionFontWeight +
                    "&descriptionFontColor="+descriptionFontColor +
                    "&descriptionText="+body + "&headerText=" + title + "&backgroundColor="+backgroundColor + "&templateId="+templateId;
            window.location.href = url;
        }
    </script>
</head>
    <body bgcolor="#F5F5F5">

        <h1>Create an Image</h1>
        <form >


            <input type="button" value="Submit" onclick="doit()"><br>
            Header Text: <input type="text" name="title" id="title" size="60"  <#if headerText??> value="${headerText}"</#if>/> <br/>
            Description: <input type="text" name="body" id="body" size="60" <#if descriptionText??> value="${descriptionText}"</#if>/>

            <br/>
            Template:
            <select id="templateId">
            <option value="">-- templates --</option>

                <#list templateIds as templateId>
                    <option value="${templateId}" <#if selectedTemplateId(templateId)??> selected</#if>>${templateId}</option>
                </#list>
            </select>


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

            Font Color: <input type="text" name="headerFontColor" id="headerFontColor" size="60" <#if headerFontColor??> value="${headerFontColor}"</#if>/>

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

            Font Color: <input type="text" name="descriptionFontColor" id="descriptionFontColor" size="60" <#if descriptionFontColor??> value="${descriptionFontColor}"</#if>/>


            <br/>
            Background Color: <input type="text" name="backgroundColor" id="backgroundColor" size="60"  <#if backgroundColor??> value="${backgroundColor}"</#if>/> <br/>


        </form>

        <br/>

        <#if adCreator.imageUrl?has_content>
        <img src="${adCreator.imageUrl}"/>
        </#if>



    </body>
</html>