<#-- @ftlvariable name="" type="com.aakn.workstore.work.view.MakeView" -->
<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<div class="container">
<#include "page-header.ftl">
    <div class="row">
        <div class="col-md-4">
            <div>
                <div class="list-group">
                <#list modelNames.names as model>
                    <a href="/${namespace}/works/make/${make}/model/${model}" class="list-group-item">${model?html}</a>
                </#list>
                </div>
            </div>
        </div>
        <div class="col-md-6">
        <#include "thumbnails.ftl">
        </div>
    </div>
</div>
<#include "footer.ftl">
<#include "scripts.ftl">
</body>
</html>