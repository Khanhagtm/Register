package com.example.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class RegistrationViewModel : ViewModel() {
    var firstName: MutableLiveData<String> = MutableLiveData("")
    var lastName: MutableLiveData<String> = MutableLiveData("")
    var birthday: MutableLiveData<String> = MutableLiveData("")
    var address: MutableLiveData<String> = MutableLiveData("")
    var email: MutableLiveData<String> = MutableLiveData("")
    var isMale: MutableLiveData<Boolean> = MutableLiveData(false)
    var isTermsAccepted: MutableLiveData<Boolean> = MutableLiveData(false)

    fun isRegistrationValid(): Boolean {
        return firstName.value!!.isNotEmpty() &&
                lastName.value!!.isNotEmpty() &&
                birthday.value!!.isNotEmpty() &&
                address.value!!.isNotEmpty() &&
                email.value!!.isNotEmpty() &&
                (isMale.value == true || isMale.value == false) &&
                isTermsAccepted.value == true
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout)

        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        val inputFirstName: EditText = findViewById(R.id.inputFirstName)
        val inputLastName: EditText = findViewById(R.id.inputLastName)
        val inputBirthday: EditText = findViewById(R.id.inputBirthday)
        val inputAddress: EditText = findViewById(R.id.inputAddress)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val radioButtonMale: RadioButton = findViewById(R.id.maleButton)
        val radioButtonFemale: RadioButton = findViewById(R.id.femaleButton)
        val checkboxTerm: CheckBox = findViewById(R.id.termsCheckbox)
        val registerButton: Button = findViewById(R.id.registerButton)

        val datePickerButton: Button = findViewById(R.id.selectDateButton)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$year-${month + 1}-$dayOfMonth"
                    inputBirthday.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }


        inputFirstName.addTextChangedListener { editable ->
            viewModel.firstName.value = editable.toString()
        }

        inputLastName.addTextChangedListener { editable ->
            viewModel.lastName.value = editable.toString()
        }

        inputBirthday.addTextChangedListener { editable ->
            viewModel.birthday.value = editable.toString()
        }

        inputAddress.addTextChangedListener { editable ->
            viewModel.address.value = editable.toString()
        }

        inputEmail.addTextChangedListener { editable ->
            viewModel.email.value = editable.toString()
        }

        radioButtonMale.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isMale.value = isChecked
        }

        radioButtonFemale.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isMale.value = isChecked
        }

        checkboxTerm.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isTermsAccepted.value = isChecked
        }

        registerButton.setOnClickListener {
            if (viewModel.isRegistrationValid()) {
                Toast.makeText(this, "Register Successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill out all information fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
