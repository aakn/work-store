<#-- @ftlvariable name="" type="com.aakn.workstore.work.view.MakeView" -->
<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<div class="container">
    <div class="row">
    <#include "page-header.ftl">
        <ol class="breadcrumb">
            <li><a href="/${namespace}/works">Home</a></li>
            <li><a href="/${namespace}/works/make/${make}">${make?capitalize?html}</a></li>
            <li class="active">${model?html}</li>
        </ol>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div>
                <div class="list-group">
                <#list modelNames.names as name>
                        <a href="/${namespace}/works/make/${make}/model/${name}"
                        class=<#if name == model>"list-group-item active"<#else>"list-group-item"</#if>>${name?html}</a>
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