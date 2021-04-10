<div
        class="modal fade"
        id="editModal${lecture.getLectureID()}"
        tabindex="-1"
        role="dialog"
        aria-labelledby="manageGroupModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    Edit Lecture
                </h5>
                <button
                        type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Close"
                >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>



            <div class="modal-body">
                <div class="card-body">
                    <div class="form-body">
                        <form method="POST" action="/edit-lecture">
                            <input hidden id="editIDInput${lecture.getLectureID()}" name="lectureID" value="${lecture.getLectureID()}" />
                            <div class="form-row">
                                <div class="col">
                                    <label for="inputEditDate${lecture.getLectureID()}">Date</label>
                                    <input type="date" class="form-control select-filter" name="date" id="inputEditDate${lecture.getLectureID()}" value="${inputDate}" required>
                                </div>
                                <div class="col">
                                    <label for="inputEditTime${lecture.getLectureID()}">Start Time</label>
                                    <input type="time" class="form-control select-filter" value="${lecture.getStartTime()}" name="startTime" id="inputEditTime${lecture.getLectureID()}" required>
                                </div>
                                <div class="col">
                                    <label for="inputEditDuration${lecture.getLectureID()}">Duration</label>
                                    <input type="number" class="form-control select-filter" value="${lecture.getDuration()}" id="inputEditDuration${lecture.getLectureID()}" name="duration" required>
                                </div>
                            </div>
                            <div class="form-row form-row-modal">
                                <label for="inputEditModule${lecture.getLectureID()}">Module</label>
                                <select class="custom-select form-control select-filter" id="inputEditModule${lecture.getLectureID()}" name="moduleID" required value="${lecture.getModule().getModuleID()}">
                                    <c:forEach var="module" items="${modules}">
                                        <option value="${module.getModuleID()}" >${module.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-row form-row-modal">
                                <label for="inputEditRoom${lecture.getLectureID()}">Room</label>
                                <select class="custom-select form-control select-filter" id="inputEditRoom${lecture.getLectureID()}" name="roomID" required>
                                    <c:forEach var="room" items="${rooms}">
                                        <option value="${room.getRoomID()}" >${room.getType()}-${room.getRoomID()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Edit Lecture
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script>

    // Set dropdowns defaults
    document.getElementById("inputEditModule${lecture.getLectureID()}").value = "${lecture.getModule().getModuleID()}";
    document.getElementById("inputEditRoom${lecture.getLectureID()}").value = "${lecture.getRoom().getRoomID()}";

</script>