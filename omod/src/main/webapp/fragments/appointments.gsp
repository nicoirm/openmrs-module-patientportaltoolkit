<script>
    jq(document).ready(function(){
        var OpenMRSInstance=window.location.href;
        var currentYear = new Date().getFullYear();
        var currentMonth = new Date().getMonth();
        var currentDay = new Date().getDate();
        var circleDateTime = new Date(currentYear, currentMonth, currentDay).getTime();
        function editEvent(event) {
            jq('#event-modal input[name="event-index"]').val(event ? event.id : '');
            jq('#event-modal input[name="event-name"]').val(event ? event.name : '');
            jq('#event-modal input[name="event-location"]').val(event ? event.location : '');
            jq('#event-modal input[name="event-start-date"]').datepicker('update', event ? event.startDate : '');
            jq('#event-modal input[name="event-end-date"]').datepicker('update', event ? event.endDate : '');
            jq('#event-modal').modal();
        }

        function deleteEvent(event) {
            var dataSource = jq('#calendar').data('calendar').getDataSource();

            for(var i in dataSource) {
                if(dataSource[i].id == event.id) {
                    dataSource.splice(i, 1);
                    break;
                }
            }

            jq('#calendar').data('calendar').setDataSource(dataSource);
        }

        function saveEvent() {
            var event = {
                id: jq('#event-modal input[name="event-index"]').val(),
                name: jq('#event-modal input[name="event-name"]').val(),
                location: jq('#event-modal input[name="event-location"]').val(),
                startDate: jq('#event-modal input[name="event-start-date"]').datepicker('getDate'),
                endDate: jq('#event-modal input[name="event-end-date"]').datepicker('getDate')
            }

            var dataSource = jq('#calendar').data('calendar').getDataSource();

            if(event.id) {
                for(var i in dataSource) {
                    if(dataSource[i].id == event.id) {
                        dataSource[i].name = event.name;
                        dataSource[i].location = event.location;
                        dataSource[i].startDate = event.startDate;
                        dataSource[i].endDate = event.endDate;
                    }
                }
            }
            else
            {
                var newId = 0;
                for(var i in dataSource) {
                    if(dataSource[i].id > newId) {
                        newId = dataSource[i].id;
                    }
                }

                newId++;
                event.id = newId;

                dataSource.push(event);
            }

            jq('#calendar').data('calendar').setDataSource(dataSource);
            jq('#event-modal').modal('hide');
        }

        function foo () {
            console.log(jq("#personUuid").val());
            //hardcoded data to be modified
            jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val(), function (reminderData) {
                //alert(dataa);
                jq('#calendar').data('calendar').setDataSource(reminderData);
            });
        }
        var calendar= jq('#calendar').calendar({
            customDayRenderer: function(element, date) {
                if(date.getTime() == circleDateTime) {
                    jq(element).css('background-color', 'red');
                    jq(element).css('color', 'white');
                    jq(element).css('border-radius', '15px');
                }
            },
            enableContextMenu: true,
            contextMenuItems:[
                {
                    text: 'Update',
                    click: editEvent
                },
                {
                    text: 'Delete',
                    click: deleteEvent
                }
            ],
            selectRange: function(e) {
                editEvent({ startDate: e.startDate, endDate: e.endDate });
            },
            mouseOnDay: function(e) {
                if(e.events.length > 0) {
                    var content = '';

                    for(var i in e.events) {
                        content += '<div class="event-tooltip-content">'
                            + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                            + '<div class="event-location">' + e.events[i].location + '</div>'
                            + '</div>';
                    }

                    jq(e.element).popover({
                        trigger: 'manual',
                        container: 'body',
                        html:true,
                        content: content
                    });

                    jq(e.element).popover('show');
                }
            },
            mouseOutDay: function(e) {
                if(e.events.length > 0) {
                    jq(e.element).popover('hide');
                }
            }

        });
        setTimeout(foo, 1000);
    });

</script>
<div class="clearfix">


    ${ ui.includeFragment("patientportaltoolkit", "markCompletedModal") }
    <input id="personUuid" value="${ person.uuid}" type="hidden">
    <h4>Upcoming Appointments</h4>
    <div>
        <table class="table table-hover" id="due-appointments">
            <thead>
            <tr>
                <th>Appointment Type</th>
                <th>Recommended Date</th>
                <% if(isACareGiver != 1) { %>
                <th>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% alertablereminders.each { reminder -> %>
            <% def date = new Date() %>
            <% if (reminder.targetDate < date-90 || reminder.targetDate > date+90) {%>
            <% }else { %>
            <% if (reminder.status == 2 && reminder.responseDate > date) {%>
            <% }else { %>
            <tr class="datarow">
                <td>  ${(reminder.followProcedureName)}</td>
                <td class="clearfix">
                    <span class="pull-left"> ${pptutil.formatDate((reminder.targetDate))}</span>
                </td>
                <% if(isACareGiver != 1) { %>
                <td class="clearfix">
                    <a id="markCompletedReminder${(reminder.id)}" class="btn btn-primary btn-sm markCompletedReminder" data-toggle="modal" data-target="#markCompleted-modal">Mark Completed</a>
                </td>
                <% } %>
            </tr>
            <% } %>
            <% } %>
            <% } %>
            </tbody>
            </table>
    </div>
    <!--<div id="chart" width="100%">
    </div>-->
    <br/>
    <div id='calendar' class="calendar" width="100%"></div>
</div>