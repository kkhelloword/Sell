<html>
<#include "../common/header.ftl">
<body>

<div id="wrapper" class="toggled">
<#--边栏sidebar-->
        <#include "../common/nav.ftl">
<#--主要内容 content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDto.orderId}</td>
                            <td>${orderDto.orderAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                <#--订单详情表数据-->
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>
                                商品id
                            </th>
                            <th>
                                商品名称
                            </th>
                            <th>
                                价格
                            </th>
                            <th>
                                数量
                            </th>
                            <th>
                                总额
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                <#list  orderDto.orderDetails as orderDetail>
                <tr>
                    <td>${orderDetail.productId}</td>
                    <td>${orderDetail.productName}</td>
                    <td>${orderDetail.productPrice}</td>
                    <td>${orderDetail.productQuantity}</td>
                    <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                <#--操作-->
                    <div class="col-md-12 column">
                <#if orderDto.getOrderStatus().getMessage() == '新订单' >
                    <a href="/sell/seller/order/finish?orderId=${orderDto.orderId}" class="btn btn-default btn-primary">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}" class="btn btn-default btn-danger">取消订单</a>
                </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>