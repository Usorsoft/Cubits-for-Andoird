#!/bin/bash

# Start script
PAGES_DIRECTORY_PATH="example/src/main/java/com/mp/example/pages/"
PACKAGE_NAME="com.mp.example"
HAS_STORE=true

# Check if package nam and paths are defined
if [ ! -f .cubit_script_store ]; then
  echo "NO cubit_script_store"
  HAS_STORE=false
fi



# Read user input
if [ $HAS_STORE == false ]; then
  echo "Enter destination path starting from root. For example 'example/src/main/java/com/mp/example/pages/':"
  read -r PAGES_DIRECTORY_PATH

  echo "Enter package name. For example 'example.company.app'"
  read -r PACKAGE_NAME
fi

echo "Enter the page name:"
read -r PAGE_NAME_INPUT

echo "Create actions? [y/n]"
read -r WANT_ACTIONS_INPUT



# Creating store
if [ $HAS_STORE == false ]; then
cat <<EOF >".cubit_script_store"
#!/bin/bash

PAGES_DIRECTORY_PATH=$PAGES_DIRECTORY_PATH
PACKAGE_NAME=$PACKAGE_NAME
EOF
else
chmod +x .cubit_script_store
./.cubit_script_store
fi



# Create names and paths
FOLDER_NAME=$(echo "$PAGE_NAME_INPUT" | tr '[:upper:]' '[:lower:]')
PAGE_PACKAGE="$PACKAGE_NAME.pages.$FOLDER_NAME"
FOLDER_PATH="$PAGES_DIRECTORY_PATH/$FOLDER_NAME"
ACTIONS_NAME="$PAGE_NAME_INPUT""Actions"
STATE_NAME="$PAGE_NAME_INPUT""State"
CUBIT_NAME="$PAGE_NAME_INPUT""Cubit"
VIEW_NAME="$PAGE_NAME_INPUT""View"
ACTIVITY_NAME="$PAGE_NAME_INPUT""Activity"
echo "Creating $ACTIONS_NAME, $STATE_NAME, $CUBIT_NAME, $VIEW_NAME and $ACTIVITY_NAME inside $FOLDER_NAME ..."



# Create folder
mkdir "$FOLDER_PATH"



# Create actions
if [ "$WANT_ACTIONS_INPUT" == "y" ]; then
cat <<EOF >"$FOLDER_PATH/$ACTIONS_NAME.kt"
package $PAGE_PACKAGE


sealed class $ACTIONS_NAME {
  object Foo: $ACTIONS_NAME()
  class ShowSomething(val data: String): $ACTIONS_NAME()
}
EOF
fi



# Create states
cat <<EOF >"$FOLDER_PATH/$STATE_NAME.kt"
package $PAGE_PACKAGE


sealed class $STATE_NAME {
  object Initial: $STATE_NAME()
  object Loading: $STATE_NAME()
  data class Content(val yourContent: String): $STATE_NAME()
}
EOF



# Create cubit
cat <<EOF >"$FOLDER_PATH/$CUBIT_NAME.kt"
package $PAGE_PACKAGE

import com.mp.cubit.Cubit


class $CUBIT_NAME : Cubit<$STATE_NAME>() {

  override fun initState() = $STATE_NAME.Initial

  override fun onDispose() {
    //TODO: Dispose your stuff
  }
}
EOF



# Create view
cat <<EOF >"$FOLDER_PATH/$VIEW_NAME.kt"
package $PAGE_PACKAGE

import androidx.compose.runtime.Composable
import com.mp.cubit.StateBuilder
import com.mp.cubit.StateViewOf


class $VIEW_NAME(cubit: $CUBIT_NAME): StateViewOf<$CUBIT_NAME, $STATE_NAME>(cubit) {

    @Composable
    fun Compose() {
        // Use StateBuilder so rebuild part of the view
        StateBuilder {
            //TODO: Compose UI ...
        }
    }
}
EOF



# Create activity
cat <<EOF >"$FOLDER_PATH/$ACTIVITY_NAME.kt"
package $PAGE_PACKAGE

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.mp.cubit.cubit_owner.CubitActivity
import com.mp.cubit.foundation.NavigationAction
import com.mp.cubit.foundation.NavigateBack


class $ACTIVITY_NAME : CubitActivity<$CUBIT_NAME, $STATE_NAME>() {

    private lateinit var view: $VIEW_NAME
    override fun onCreateCubit(): $CUBIT_NAME {
      return $CUBIT_NAME()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = $VIEW_NAME(cubit)
        cubit.addActionListener(::onNavigationAction, condition = { it is NavigationAction })
        setContent { view.Compose() }
    }

    private fun onNavigationAction(action: NavigationAction) {
        when(action) {
            is NavigateBack -> onBackPressed()
        }
    }

    companion object {
        fun start(activity: AppCompatActivity, cubitId: String? = null) {
            val intent = Intent(activity.baseContext, $ACTIVITY_NAME::class.java)
            cubitId?.let { intent.putExtras(Bundle().apply { putString("cubit_id", it) }) }
            activity.startActivity(intent)
        }
    }
}
EOF