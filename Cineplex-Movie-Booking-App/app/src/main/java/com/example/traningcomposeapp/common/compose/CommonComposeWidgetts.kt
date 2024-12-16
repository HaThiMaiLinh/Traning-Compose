package com.example.traningcomposeapp.common.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.ui.theme.Black
import com.example.traningcomposeapp.ui.theme.TextStyleBold
import com.example.traningcomposeapp.ui.theme.TextStyleBold18
import com.example.traningcomposeapp.ui.theme.TextStyleLight8
import com.example.traningcomposeapp.ui.theme.TextStyleMedium18
import com.example.traningcomposeapp.ui.theme.TextStyleNormal14
import com.example.traningcomposeapp.ui.theme.fontFamily
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    title: String = "",
    showNavigationIcon: Boolean = true,
    onBackPressed: () -> Unit = {},
) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.white),
                style = TextStyleMedium18,
                modifier = modifier
            )
        },
        navigationIcon = {
            if (showNavigationIcon) {
                IconButton(onClick = { onBackPressed() }) {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(id = R.color.black)
        )
    )
}

@Composable
fun CenterAlignedButton(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyleBold,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(24.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.widget_background_1),
        contentColor = colorResource(id = R.color.black)
    ),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        onClick = {
            onClick()
        }) {
        Text(
            text = text, style = textStyle
        )
    }
}

@Composable
fun CenterAlignedOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = TextStyleBold,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(24.dp),
    border: BorderStroke = BorderStroke(
        width = 1.dp,
        color = colorResource(id = R.color.widget_background_2)
    ),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.transparent),
        contentColor = colorResource(id = R.color.white)
    ),
    onClick: () -> Unit
) {

    OutlinedButton(modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        onClick = {
            onClick()
        }) {
        Text(
            text = text, style = textStyle
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithIndicator(pagerState: PagerState, content: @Composable (Int) -> Unit) {
    HorizontalPager(state = pagerState, beyondBoundsPageCount = 2) { page ->
        content(page)
    }
}

@Composable
fun TermsAndPrivacyText() {
    Text(
        text = stringResource(id = R.string.agree_terms_condition),
        color = colorResource(id = R.color.widget_background_2),
        textAlign = TextAlign.Center,
        style = TextStyleLight8,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TextFieldCompose(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = TextStyleNormal14,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    placeholder: @Composable (() -> Unit)?,
    colors: TextFieldColors,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None // Thêm tham số này
) {
    TextField(
        value = textFieldValue,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        textStyle = textStyle,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        placeholder = placeholder,
        colors = colors,
        visualTransformation = visualTransformation,
        modifier = modifier
    )
}

@Composable
fun TextFieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    errorMessage: String = "",
    isPassword: Boolean = false

) {
    val context = LocalContext.current

    TextFieldCompose(
        textFieldValue = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyleNormal14,
        keyboardType = keyboardType,
        imeAction = imeAction,
        leadingIcon = leadingIcon,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = colorResource(id = R.color.widget_background_2)
            )
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(id = R.color.white),
            focusedContainerColor = colorResource(id = R.color.transparent),
            unfocusedContainerColor = colorResource(id = R.color.transparent),
            focusedIndicatorColor = colorResource(id = R.color.widget_background_1),
            disabledIndicatorColor = colorResource(id = R.color.white),
            cursorColor = colorResource(id = R.color.white)
        ),
    )

    if (isError && errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }

}

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyleBold18,
        color = colorResource(id = R.color.widget_background_4),
        modifier = modifier
    )
}
