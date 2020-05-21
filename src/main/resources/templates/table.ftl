<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Nauka</title>
</head>
<body style="background-color: #f2f2f2">
<br>
<br>
<div>
    <div>
        <div style="float: left;">
            <form action="/" method="post">
                <label for="dpt" style="margin-left: 20px">Отдел: </label>
                <select id="dpt" name="selectedDepartment">
                    <#list departments as department>
                        <#if department.deptName == chosenDept.deptName>
                            <option value="${department.deptName}" selected>${department.deptName}</option>
                        <#else>
                            <option value="${department.deptName}">${department.deptName}</option>
                        </#if>
                    </#list>
                </select>
                <label for="selectedMonth" style="margin-left: 20px">Месяц: </label>
                <select id="selectedMonth" name="selectedMonth">
                    <#list months as month>
                        <#if month.monthName == chosenMonth.monthName>
                            <option value="${month.monthName}" selected>${month.monthName}</option>
                        <#else>
                            <option value="${month.monthName}">${month.monthName}</option>
                        </#if>
                    </#list>
                </select>
                <button type="submit" style="margin-left: 20px">Выбрать</button>
            </form>
        </div>
        <br>
        <br>
        <div style="float: left;">
            <table style="border:1px solid; margin-left: 20px">
                <thead style="background-color: #e6e6e6">
                <tr>
                    <th align="left">Ф.И.О.</th>
                    <th align="left" width="120px">Должность</th>
                    <th align="left" width="30px">ID</th>
                    <#list 0..dates?size-1 as i>
                        <#assign x = days[i]>
                        <#if x == false>
                            <th width="20px">${dates[i]}</th>
                        <#else>
                            <th width="20px" bgcolor="#ffcccc">${dates[i]}</th>
                        </#if>
                    </#list>
                    <th style="padding-left: 10px" align="left" width="120px">Итого</th>
                </tr>
                </thead>
                <tbody>
                <#list employees as employee>
                <tr>
                    <td>${employee.name}</td>
                    <td>${employee.position}</td>
                    <td>${employee.empId}</td>
                    <#list empStats as k, v>
                        <#if k == employee.empId>
                            <#list 0..v?size-2 as i>
                                <#assign d = days[i]>
                                <#if d == true>
                                    <td bgcolor="#ffcccc" style="font-size: small; text-align: center">${v[i]}</td>
                                <#else>
                                    <td style="font-size: small; text-align: center">${v[i]}</td>
                                </#if>
                            </#list>
                            <td style="font-size: small; padding-left: 10px">${v[v?size-1]}</td>
                            <#break>
                        </#if>
                    </#list>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>