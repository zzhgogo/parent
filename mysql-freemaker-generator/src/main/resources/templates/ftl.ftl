<style>
    /* 注意所有本页的样式，必须以 #page${entityLowerName}     开头！！！*/
</style>

<div id="page${entityName}" class="wrapper-page-inner">
    <!-- 查询表单 -->
    <i-form :model="formSearch" ref="formSearch" inline>
        <Form-item label="创建时间">
            <Form-item prop="createdateStart">
                <Date-picker type="datetime" placeholder="点击选择时间" v-model="formSearch.createdateStart" size="small"
                             clearable="false"></Date-picker>
            </Form-item>
            <Form-item>至</Form-item>
            <Form-item prop="createdateEnd">
                <Date-picker type="datetime" placeholder="点击选择时间" v-model="formSearch.createdateEnd" size="small"
                             clearable="false"></Date-picker>
            </Form-item>
        </Form-item>
        <Form-item>
            <i-button type="primary" @click="submitSearch('formSearch')" size="small">{{label.search}}</i-button>
            <i-button type="ghost" style="margin-left: 8px" @click="resetSearch('formSearch')" size="small">
                {{label.clear}}
            </i-button>
        ${'<@shiro.hasPermission'}
            name="upms:${entityLowerName}:add">
            <i-button type="primary" style="margin-left: 8px" @click="addRow" size="small">{{label.add}}</i-button>
        ${'</@shiro.hasPermission'}
            >
        </Form-item>
    </i-form>

    <!-- 数据表格 -->
    <i-table :columns="columns" :data="pager.data" size="small" @on-select="onSelect"
             @on-selection-change="onSelectionChange"></i-table>
    <!-- 翻页 -->
    <div class="wrapper-pagination">
        <Page :total="pager.total" :current="pager.nowpage" :page-size="pager.pagesize" @on-change="pagingNumChange"
              @on-page-size-change="pagingSizeChange" placement="top" show-total show-elevator show-sizer></Page>
    </div>
    <!-- 弹窗 -->
    <!-- 弹窗 -->
    <Modal v-model="dialogShow" :title="label[currDialog]" :mask-closable="false" width="750"
           @on-cancel="resetDialogForm('formDialog')">
        <i-form :model="formDialog" ref="formDialog" :rules="rules" :label-width="80">
            <i-input v-model="formDialog.id" type="hidden"></i-input>
        <#assign  flag="true"/>
        <#list entityFieldList as field>
            <#if field.columnName == 'id' || field.columnName == 'create_by' || field.columnName == 'modify_by' || field.columnName == 'create_time' || field.columnName == 'modify_time'>
            <#else>
                <#if (field_index+1) % 2 == 0>
                    <#assign flag="false"/>
                <Row>
                    <i-col span="12">
                        <Form-item label="${field.comment}" prop="${field.lowerPropertyName}">
                            <i-input v-model="formDialog.${field.lowerPropertyName}"
                                     placeholder="请输入${field.comment}"></i-input>
                        </Form-item>
                    </i-col>
                <#else>
                    <#assign flag="true"/>
                    <i-col span="12">
                        <Form-item label="${field.comment}" prop="${field.lowerPropertyName}">
                            <i-input v-model="formDialog.${field.lowerPropertyName}"
                                     placeholder="请输入${field.comment}"></i-input>
                        </Form-item>
                    </i-col>
                </Row>
                </#if>
            </#if>
        </#list>
        <#if flag=="false">
            </Row>
        </#if>
        </i-form>
        <div slot="footer">
            <i-button @click="resetDialogForm('formDialog')">{{label.clear}}</i-button>
            <i-button type="primary" @click="submitDialogForm('formDialog')" :loading="dialogSubmitLoading">
                {{label.submit}}
            </i-button>
        </div>
    </Modal>
</div>
<script>
    "use strict";
    ss.mvvm({
        id: 'page${entityName}',
    data: {
        url: {
            add: '${entityLowerName}/add.do',
            edit: '${entityLowerName}/edit.do',
            delete: '${entityLowerName}/delete.do'
        },
        formSearch: {
            createdateStart: '',
            createdateEnd: ''
        },
        columns: [
            {
                type: 'selection',
                width: 80,
                fixed: 'left',
                align: 'center'
            },<#list entityFieldList as field>
            <#if field.lowerPropertyName != 'id'>
                {
                    "title": "${field.comment}",
                    "key": "${field.lowerPropertyName}",
                    "width": 150,
                    "sortable": true
                },
            </#if>
        </#list> {
                title: '操作',
                key: 'action',
                width: 140,
                align: 'center',
                fixed: 'right',
                render: function (create, params) {
                    var vm = page${entityName};
                    return create('div', [
                        ${'<@shiro.hasPermission'}name = "upms:${entityLowerName}:edit" >
                                vm.genBtnEdit(vm, create, params),
                    ${'</@shiro.hasPermission'} >
                    ${'<@shiro.hasPermission'} name = "upms:${entityLowerName}:delete" >
                            vm.genBtnDelete(vm, create, params)
                            ${'</@shiro.hasPermission'} >
                    ])
                    ;
                }
            }
        ],
    formDialog: {
        id: 0<#list entityFieldList as field>
        <#if field.columnName == 'id' || field.columnName == 'create_by' || field.columnName == 'modify_by' || field.columnName == 'create_time' || field.columnName == 'modify_time'>
        <#else>
            ,${field.lowerPropertyName}:
        ''
        </#if>
    </#list>
    },
    rules: {

    }
    },
    watch: {

    },
    pager: {
        url: '${entityLowerName}/dataGrid.do',
        sort:'createTime',
        order:'desc'
    },
    methods: {
        onSelect: function (selection, row) {
            //selection: array
            //row: data of selected row
            //console.log(selection, row)
        },
        onSelectionChange: function (selection) {
            // selection: array
            //console.log(selection);
        }
    },
    domReady: function () {
        var vm = this;
        this.paging();
    }
    })
    ;
    // end of ss.mvvm
</script>