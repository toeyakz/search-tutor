package com.example.searchtutor.data.response

import com.example.searchtutor.data.model.User


data class CategoryNameResponse(
    val isSuccessful: Boolean,
    val data: List<CategoryName>?
) {
    data class CategoryName(
        val g_id: Int?,
        val ca_name: String?
    )
}

data class CategoryResponse(
    val isSuccessful: Boolean,
    val data: List<Category>?
) {
    data class Category(
        val ca_id: Int?,
        val ca_name: String?
    )
}

data class AddGroupCategoryResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class DeleteCourseResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class UpdateCourseResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class AddCourseResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class UpdateGroupCategoryResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class GroupCourseResponse(
    val isSuccessful: Boolean,
    val data: List<GroupCourse>?
) {
    data class GroupCourse(
        val gc_id: Int?,
        val g_id: Int?,
        val t_id: Int?,
        val gc_name: String?,
        val gc_detail: String?,
        val gc_price: Int?
    )
}

data class TutorListResponse(
    val isSuccessful: Boolean,
    val data: List<TutorList>?
) {
    data class TutorList(
        val t_id: Int?,
        val t_username: String?,
        val t_password: String?,
        val t_name: String?,
        val t_lname: String?,
        val t_email: String?,
        val t_tel: Int?,
        val t_address: String?,
        val t_img: String?,
        val count_course: Int?
    )
}

data class CommentResponse(
    val isSuccessful: Boolean,
    val data: List<Comment>?
) {
    data class Comment(
        val cm_id: Int?,
        val cmk_id: Int?,
        val t_id: Int?,
        val st_id: Int?,
        val cm_detail: String?,
        val st_name: String?,
        val st_lname: String?,
        val st_img: String?,
        val t_name: String?,
        val t_lname: String?,
        val t_img: String?,
        val cm_datetime: String?
    )
}

data class AddCommentResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class TutorResponse(
    val isSuccessful: Boolean,
    val data: List<User>?
)

data class StudentResponse(
    val isSuccessful: Boolean,
    val data: List<User>?
)
data class ImageReturn (
    val isSuccessful: Boolean,
    val message: String?
)


